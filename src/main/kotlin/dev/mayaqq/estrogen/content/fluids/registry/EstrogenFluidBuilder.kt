package dev.mayaqq.estrogen.content.fluids.registry

import dev.mayaqq.cynosure.client.render.RenderLayerMap
import earth.terrarium.botarium.common.registry.fluid.*
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Fluid
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.builders.Builder
import uwu.serenity.kritter.api.builders.BuilderCallback
import uwu.serenity.kritter.api.entry.Delegate
import uwu.serenity.kritter.api.entry.RegistryEntry
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.internal.NotUsableInBuilder
import uwu.serenity.kritter.stdlib.BlockBuilder
import uwu.serenity.kritter.stdlib.ItemBuilder
import uwu.serenity.kritter.stdlib.location


@Suppress("UNCHECKED_CAST")
inline fun <S : BotariumSourceFluid, F : BotariumFlowingFluid> Registrar<Fluid>.fluid(
    name: String,
    noinline sourceFactory: (FluidData) -> S,
    noinline flowingFactory: (FluidData) -> F,
    builder: FluidBuilder<S, F>.() -> Unit = {}
): EstrogenFluidEntry<S, F> = FluidBuilder(name, this, this.getCallback(), sourceFactory, flowingFactory).apply(builder).register() as EstrogenFluidEntry<S, F>

class FluidBuilder<S : BotariumSourceFluid, F : BotariumFlowingFluid>(
    name: String,
    owner: Registrar<Fluid>,
    callback: BuilderCallback<Fluid, S>,
    private val sourceFactory: (FluidData) -> S,
    private val flowingFactory: (FluidData) -> F,
    private val flowingKey: ResourceKey<Fluid> = owner.createResourceKey("flowing_$name")
) : Builder<Fluid, S>(name, owner, callback) {

    private var fluidData: FluidData? = null
    private var flowingWrapper: RegistryEntry<F>? = null
    private var blockEntry: RegistryEntry<BotariumLiquidBlock>? = null
    private var bucketEntry: RegistryEntry<FluidBucketItem>? = null
    private var _properties: ((FluidProperties.Builder) -> Unit)? = null

    /**
     * Set the properties of this block
     * @param props property builder function
     */
    fun properties(props: FluidProperties.Builder.() -> Unit) {
        this._properties = props
        buildProperties()
        flowingWrapper = owner.getCallback<F>().invoke(flowingKey, null, this::createFlowingEntry, this::wrapFlowing)
    }

    fun renderType(renderType: () -> RenderType) {
        onRegister {
            clientOnly {
                RenderLayerMap.putFluid(it, renderType.invoke())
                RenderLayerMap.putFluid(flowingWrapper!!.value, renderType.invoke())
            }
        }
    }

    @OptIn(NotUsableInBuilder::class)
    fun <B : BotariumLiquidBlock> block(
        factory: (FluidData, BlockBehaviour.Properties) -> B,
        name: String = this.name,
        builder: BlockBuilder<BotariumLiquidBlock>.() -> Unit = {}
    ) {
        val blocks = owner.sibling(Registries.BLOCK)
        this.blockEntry = BlockBuilder(
            name,
            blocks,
            blocks.getCallback()
        ) { factory(this.result!!.value.data, it) as BotariumLiquidBlock }.apply(builder).register()
    }

    @OptIn(NotUsableInBuilder::class)
    fun <I : FluidBucketItem> bucket(
        factory: (FluidData, Item.Properties) -> I,
        name: String = this.name,
        builder: ItemBuilder<FluidBucketItem>.() -> Unit = {}
    ) {
        val items = owner.sibling(Registries.ITEM)
        this.bucketEntry = ItemBuilder(
            name + "_bucket",
            items,
            items.getCallback()
        ) { factory(this.result!!.value.data, it) as FluidBucketItem }.apply(builder).register()
    }



    private fun buildProperties() {
        val builder = FluidProperties.create()
        if (_properties != null)  _properties!!.invoke(builder)
        if (owner is FluidRegistryProvider) {
            fluidData = (owner as FluidRegistryProvider).fluidRegistry.register(builder.build(key.location))
        } else {
            throw Exception("Your Fluid Registrar must implement FluidRegistryProvider")
        }
    }

    private fun createFlowingEntry() : F = flowingFactory.invoke(fluidData!!)
    private fun wrapFlowing(delegate: Delegate<F>) = RegistryEntry(flowingKey, delegate, mutableSetOf())

    override fun createEntry(): S {
        return sourceFactory.invoke(fluidData!!)
    }
    override fun wrapEntry(delegate: Delegate<S>): EstrogenFluidEntry<S, F> = EstrogenFluidEntry(
        key,
        delegate,
        fluidData!!,
        flowingWrapper!!,
        blockEntry!!,
        bucketEntry!!
    )
}