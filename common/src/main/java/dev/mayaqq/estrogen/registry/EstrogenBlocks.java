package dev.mayaqq.estrogen.registry;

import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;
import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.simibubi.create.content.redstone.displayLink.source.EntityNameDisplaySource;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenSpriteShifts;
import dev.mayaqq.estrogen.registry.blocks.*;
import dev.mayaqq.estrogen.registry.items.DreamBottleItem;
import dev.mayaqq.estrogen.utils.StatePredicates;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import uwu.serenity.critter.stdlib.blocks.BlockEntry;
import uwu.serenity.critter.stdlib.blocks.BlockRegistrar;

public class EstrogenBlocks {
    public static final BlockRegistrar BLOCKS = BlockRegistrar.create(Estrogen.REGISTRIES);

    public static final BlockEntry<CentrifugeBlock> CENTRIFUGE = BLOCKS.entry("centrifuge", CentrifugeBlock::new)
        .copyProperties(SharedProperties::copperMetal)
        .properties(p -> p.requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_ORANGE)
            .noOcclusion())
        .renderType(() -> RenderType::cutout)
        .transform(Transgenders.stressImpact(8.0))
        .simpleItem()
        .register();

    public static final BlockEntry<CookieJarBlock> COOKIE_JAR = BLOCKS.entry("cookie_jar", CookieJarBlock::new)
        .copyProperties(() -> Blocks.GLASS)
        .properties(p -> p.sound(EstrogenSoundTypes.COOKIE_JAR))
        .renderType(() -> RenderType::cutout)
        .item(BlockItem::new)
        .transform(Transgenders.standardTooltip())
        .build()
        .register();

    public static final BlockEntry<DreamBlock> DREAM_BLOCK = BLOCKS.entry("dream_block", DreamBlock::new)
        .copyProperties(() -> Blocks.END_GATEWAY)
        .properties(p -> p.pushReaction(PushReaction.NORMAL)
            .isSuffocating(StatePredicates::never)
            .sound(EstrogenSoundTypes.DREAM_BLOCK)
            .dynamicShape())
        .item("dream_bottle", DreamBottleItem::new)
        .properties(p -> p.rarity(Rarity.EPIC))
        .build()
        .register();


    public static final BlockEntry<DormantDreamBlock> DORMANT_DREAM_BLOCK = BLOCKS.entry("dormant_dream_block", DormantDreamBlock::new)
        .properties(p -> p.mapColor(MapColor.DIAMOND)
            .instrument(NoteBlockInstrument.HAT)
            .strength(3.0F)
            .noOcclusion()
            .requiresCorrectToolForDrops()
            .isRedstoneConductor(StatePredicates::always)
            .sound(EstrogenSoundTypes.DORMANT_DREAM_BLOCK)
            .isValidSpawn(StatePredicates::never)
            .isSuffocating(StatePredicates::never)
            .isViewBlocking(StatePredicates::never))
        .renderType(() -> RenderType::translucent)
        .onRegister(CreateRegistrate.blockModel(() -> EstrogenSpriteShifts.DORMANT_DREAM_BLOCK_PROVIDER)::accept)
        .item(BlockItem::new)
        .transform(Transgenders.standardTooltip())
        .build()
        .register();


    public static final BlockEntry<Block> MOTH_WOOL = BLOCKS.entry("moth_wool", Block::new)
        .copyProperties(() -> Blocks.ORANGE_WOOL)
        .simpleItem()
        .register();
    public static final BlockEntry<Block> QUILTED_MOTH_WOOL = BLOCKS.entry("quilted_moth_wool", Block::new)
        .copyProperties(() -> Blocks.ORANGE_WOOL)
        .simpleItem()
        .register();

    public static final BlockEntry<CarpetBlock> MOTH_WOOL_CARPET = BLOCKS.entry("moth_wool_carpet", CarpetBlock::new)
        .copyProperties(() -> Blocks.ORANGE_CARPET)
        .simpleItem()
        .register();
    public static final BlockEntry<CarpetBlock> QUILTED_MOTH_WOOL_CARPET = BLOCKS.entry("quilted_moth_wool_carpet", CarpetBlock::new)
        .copyProperties(() -> Blocks.ORANGE_CARPET)
        .simpleItem()
        .register();

    public static final BlockEntry<SeatBlock> MOTH_SEAT = BLOCKS.entry("moth_seat", p -> new SeatBlock(p, null))
        .copyProperties(() -> Blocks.STRIPPED_SPRUCE_WOOD)
        .properties(p -> p.mapColor(DyeColor.ORANGE))
        .onRegister(AllMovementBehaviours.movementBehaviour(new SeatMovementBehaviour())::accept)
        .onRegister(AllInteractionBehaviours.interactionBehaviour(new SeatInteractionBehaviour())::accept)
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(new EntityNameDisplaySource(), "entity_name")::accept)
        .simpleItem()
        .register();

    public static final BlockEntry<EstrogenPillBlock> ESTROGEN_PILL_BLOCK = BLOCKS.entry("estrogen_pill_block", EstrogenPillBlock::new)
        .copyProperties(() -> Blocks.OAK_PLANKS)
        .properties(p -> p.strength(1.0f, 1.0f)
            .sound(EstrogenSoundTypes.PILL_BOX))
        .simpleItem()
        .register();

    public static final BlockEntry<ModelBedBlock> MOTH_BED = BLOCKS.entry("moth_bed", ModelBedBlock::new)
        .copyProperties(() -> Blocks.ORANGE_BED)
        .item(BlockItem::new)
            .properties(p -> p.stacksTo(1))
        .build()
        .register();

    public static final BlockEntry<ModelBedBlock> QUILTED_MOTH_BED = BLOCKS.entry("quilted_moth_bed", ModelBedBlock::new)
        .copyProperties(() -> Blocks.ORANGE_BED)
            .item(BlockItem::new)
            .properties(p -> p.stacksTo(1))
            .build()
        .register();
}