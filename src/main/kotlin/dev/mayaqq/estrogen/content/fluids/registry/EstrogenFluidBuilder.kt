@file:Suppress("UnstableApiUsage")

package dev.mayaqq.estrogen.content.fluids.registry

import com.teamresourceful.resourcefullib.client.fluid.data.ClientFluidProperties
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulBucketItem
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulFlowingFluid
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulLiquidBlock
import com.teamresourceful.resourcefullib.common.fluid.data.FluidData
import com.teamresourceful.resourcefullib.common.fluid.data.FluidProperties
import dev.mayaqq.cynosure.client.render.RenderLayerMap
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.Builder
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import invoke.kitty.kritter.registry.block.BlockBuilder
import invoke.kitty.kritter.registry.item.ItemBuilder
import invoke.kitty.kritter.utils.clientOnly
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Fluid
import kotlin.invoke


@Suppress("UNCHECKED_CAST")
inline fun <S : ResourcefulFlowingFluid.Still, F : ResourcefulFlowingFluid.Flowing> Registrar<Fluid>.fluid(
    name: String,
    noinline sourceFactory: (FluidData) -> S,
    noinline flowingFactory: (FluidData) -> F,
    builder: FluidBuilder<S, F>.() -> Unit = {}
): EstrogenFluidEntry<S, F> = FluidBuilder(name, this, this.createBuilderCallback(name), sourceFactory, flowingFactory).apply(builder).register() as EstrogenFluidEntry<S, F>

class FluidBuilder<S : ResourcefulFlowingFluid.Still, F : ResourcefulFlowingFluid.Flowing>(
    name: String,
    owner: Registrar<Fluid>,
    callback: Registrar.BuilderCallback<Fluid, S>,
    private val sourceFactory: (FluidData) -> S,
    private val flowingFactory: (FluidData) -> F,
    private val flowingName: String = "flowing_$name"
) : Builder<Fluid, S>(owner, callback) {

    private var fluidData: com.teamresourceful.resourcefullib.common.registry.RegistryEntry<FluidData>? = null
    var flowingWrapper: RegistryEntry<F>? = null
    private var blockEntry: RegistryEntry<ResourcefulLiquidBlock>? = null
    private var bucketEntry: RegistryEntry<ResourcefulBucketItem>? = null
    private var _properties: ((FluidProperties.Builder) -> Unit)? = null
    private var clientProperties: ClientFluidProperties? = null

    /**
     * Set the properties of this fluid
     * @param props property builder function
     */
    fun properties(props: FluidProperties.Builder.() -> Unit) {
        this._properties = props
        buildProperties()
        owner.createBuilderCallback<F>(flowingName).apply {
            flowingWrapper = this@FluidBuilder.wrapFlowing(this.key) as RegistryEntry<F>
            acceptEntry(this@FluidBuilder::createFlowingEntry, flowingWrapper!!)
        }
    }

    /**
     * Set the client properties of this fluid
     * @param props property builder function
     */
    fun clientProperties(props: ClientFluidProperties.Builder.() -> Unit) {
        val builder = ClientFluidProperties.builder()
        props.invoke(builder)
        clientProperties = builder.build()
        clientOnly {
            if (clientProperties != null) {
                (owner as FluidRegistryProvider).clientFluidRegistry.register(name) { clientProperties }
            }
        }
    }

    fun renderType(renderType: () -> RenderType) {
        onRegister {
            clientOnly {
                RenderLayerMap.putFluids(renderType.invoke(), it.source, it.flowing)
            }
        }
    }

    //@OptIn(NotUsableInBuilder::class)
    fun <B : ResourcefulLiquidBlock> block(
        factory: (FluidData, BlockBehaviour.Properties) -> B,
        name: String = this.name,
        builder: BlockBuilder<ResourcefulLiquidBlock>.() -> Unit = {}
    ) {
        val blocks = owner.sibling(Registries.BLOCK)
        this.blockEntry = BlockBuilder(
            blocks,
            blocks.createBuilderCallback(name)
        ) { factory(this.result!!.value!!.data, it) as ResourcefulLiquidBlock }.apply(builder).register()
    }

    //@OptIn(NotUsableInBuilder::class)
    fun <I : ResourcefulBucketItem> bucket(
        factory: (FluidData, Item.Properties) -> I,
        name: String = this.name,
        builder: ItemBuilder<ResourcefulBucketItem>.() -> Unit = {}
    ) {
        val items = owner.sibling(Registries.ITEM)
        this.bucketEntry = ItemBuilder(
            items,
            items.createBuilderCallback("${name}_bucket")
        ) { factory(this.result!!.value!!.data, it) as ResourcefulBucketItem }.apply(builder).register()
    }



    private fun buildProperties() {
        val builder = FluidProperties.builder()
        if (_properties != null)  _properties!!.invoke(builder)
        if (owner is FluidRegistryProvider) {
            fluidData = (owner as FluidRegistryProvider).fluidRegistry.register(key.location().path, builder.build())
        } else {
            throw Exception("Your Fluid Registrar must implement FluidRegistryProvider")
        }
    }

    private fun createFlowingEntry() : F = flowingFactory.invoke(fluidData!!.get())
    private fun wrapFlowing(key: ResourceKey<Fluid>) = RegistryEntry(key)

    override fun createEntry(): S {
        return sourceFactory.invoke(fluidData!!.get())
    }

    override fun register(): RegistryEntry<S> {
        val entry = EstrogenFluidEntry<S, F>(key, {fluidData!!.get()}, flowingWrapper!!, blockEntry!!, bucketEntry!!)
        handler.acceptEntry(this::createEntry, entry)
        return entry

    }
}