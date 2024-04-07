package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import dev.mayaqq.estrogen.utils.DynamicTextureMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Math;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

@Environment(EnvType.CLIENT)
public class DreamBlockTexture {

    public static int maxAnimTick = useFabulousGraphics() ? EstrogenConfig.client().animSpeedFabulous.get() : EstrogenConfig.client().animSpeedNormal.get();
    public static int currentAnimationTick = 0;
    private final DynamicTextureMap map;
    private final DreamBlockEntity blockEntity;
    private final Map<Direction, Set<Goober>> goobers = new Object2ObjectArrayMap<>(6);
    private final RandomSource random;

    public DreamBlockTexture(DreamBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
        this.map = new DynamicTextureMap(16);
        BlockPos pos = blockEntity.getBlockPos();
        this.random = RandomSource.create(pos.getX() + pos.getY() * 111L + pos.getZ() * 17L); // Unique seed for each BlockPos
    }

    public static void animationTick() {
        currentAnimationTick++;
        if(currentAnimationTick > maxAnimTick) currentAnimationTick = 0;
    }

    public void init() {
        this.populateNodes();
        map.init(Minecraft.getInstance().getTextureManager(), "dream_block");
        map.drawAll(this::draw);
    }

    public void animate() {
        if(!Minecraft.useFancyGraphics()) return;
        int ct = currentAnimationTick;
        for(Direction direction : Direction.values()) {
            for(Goober goober : goobers.get(direction)) {
                if(ct == goober.startTick() || ct == goober.endTick()) {
                    map.draw(direction, this::draw);
                }
            }
        }
    }

    public void redraw() {
        map.drawAll(this::draw);
    }

    public void renderFace(Matrix4f pose, VertexConsumer consumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction direction, boolean cull) {
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();

        // Simple face culling TODO: better way of doing this
        if(cull && level != null && level.getBlockState(pos.relative(direction)).is(EstrogenBlocks.DREAM_BLOCK.get())) return;

        consumer.vertex(pose, x0, y0, z0).color(0xFFFFFFFF).uv(map.getU0(direction), map.getV0(direction))
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(pose.normal(new Matrix3f()), 0, 1, 0).endVertex();
        consumer.vertex(pose, x1, y0, z1).color(0xFFFFFFFF).uv(map.getU1(direction), map.getV0(direction))
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(pose.normal(new Matrix3f()), 0, 1, 0).endVertex();
        consumer.vertex(pose, x1, y1, z2).color(0xFFFFFFFF).uv(map.getU1(direction), map.getV1(direction))
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(pose.normal(new Matrix3f()), 0, 1, 0).endVertex();
        consumer.vertex(pose, x0, y1, z3).color(0xFFFFFFFF).uv(map.getU0(direction), map.getV1(direction))
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(pose.normal(new Matrix3f()), 0, 1, 0).endVertex();
    }

    public DynamicTextureMap getTextureMap() {
        return map;
    }

    public RenderType getRenderType() {
        return RenderType.entityTranslucentCull(map.location());
    }

    private void populateNodes() {
        for(Direction dir : Direction.values()) {
            if(!goobers.containsKey(dir)) goobers.put(dir, new ObjectArraySet<>());
            int nodeCount = random.nextIntBetweenInclusive(6, 12);

            for (int i = 0; i < nodeCount; i++) {
                GooberColor color = GooberColor.values()[random.nextIntBetweenInclusive(0, 5)];
                GooberStyle style = GooberStyle.weighted(random);

                int posX = random.nextInt(0, 16);
                int posY = random.nextInt(0, 16);
                int startTick = 0, endTick = 0;
                boolean reverse = false;

                if(posY > 2 && posX < 14 && posX > 2 && posY < 14) {
                    boolean hasAnim = (style == GooberStyle.STAR_ANIMATED);
                    startTick = hasAnim ? random.nextIntBetweenInclusive(0, maxAnimTick / 2) : -1;
                    endTick = hasAnim ? startTick + maxAnimTick / 2 : -1;
                    reverse = random.nextBoolean();
                }

                Goober goober = new Goober(posX,
                        posY,
                        color,
                        style,
                        startTick,
                        endTick,
                        reverse,
                        getTransparency(random)
                );

                goobers.get(dir).add(goober);
            }
        }
    }

    private void draw(DynamicTextureMap.DrawContext ctx) {
        BlockPos pos = blockEntity.getBlockPos();
        Level level = blockEntity.getLevel();
        if(level != null && level.getBlockState(pos.relative(ctx.face())).is(EstrogenBlocks.DREAM_BLOCK.get())) {
            return;
        };


        ctx.applyToPixels(i -> 0xEE100005); // Draw the background

        // Draw the silly stars
        for(Goober goober : goobers.get(ctx.face())) {
            goober.draw(ctx, currentAnimationTick);
        }

         drawBorder(ctx); // Draw the connected border
    }

    private void drawBorder(DynamicTextureMap.DrawContext ctx) {
        BiPredicate<Integer, Integer> connect = connectedBorder(blockEntity, ctx.face());

        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                if(connect.test(x, y)) {
                    ctx.setPixelColor(x, y, 0xFFFFFFFF);
                }
            }
        }
    }

    protected Direction getUpDirection(Direction face) {
        Direction.Axis axis = face.getAxis();
        return axis.isHorizontal() ? Direction.UP : Direction.NORTH;
    }

    protected Direction getRightDirection(Direction face) {
        Direction.Axis axis = face.getAxis();
        return axis == Direction.Axis.X ? Direction.SOUTH : Direction.WEST;
    }

    private BiPredicate<Integer, Integer> connectedBorder(DreamBlockEntity be, Direction face) {

        BlockPos pos = be.getBlockPos();
        Level level = be.getLevel();
        Block dreamBlock = EstrogenBlocks.DREAM_BLOCK.get();

        BiPredicate<Integer, Integer> output = (x, y) -> false;

        boolean positive = face.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        Direction right = getRightDirection(face);
        Direction up = getUpDirection(face);

        if (face.getAxis() == Direction.Axis.X) {
            up = up.getOpposite();
            right = right.getOpposite();
        }

        up = positive ? up.getOpposite() : up;

        if(level == null) return output;

        BlockState stateR = level.getBlockState(pos.relative(right));
        BlockState stateL = level.getBlockState(pos.relative(right.getOpposite()));
        BlockState stateUp = level.getBlockState(pos.relative(up));
        BlockState stateDown = level.getBlockState(pos.relative(up.getOpposite()));

        if(!stateL.is(dreamBlock)) output = output.or((x, y) -> x == 15);

        if(!stateR.is(dreamBlock)) output = output.or((x, y) -> x == 0);

        if(!stateDown.is(dreamBlock)) output = output.or((x, y) -> y == 15);

        if(!stateUp.is(dreamBlock)) output = output.or((x, y) -> y == 0);

        BlockState cornerUpRight = level.getBlockState(pos.relative(up).relative(right));
        BlockState cornerUpLeft = level.getBlockState(pos.relative(up).relative(right.getOpposite()));
        BlockState cornerDownRight = level.getBlockState(pos.relative(up.getOpposite()).relative(right));
        BlockState corderDownLeft = level.getBlockState(pos.relative(up.getOpposite()).relative(right.getOpposite()));

        if(!cornerUpLeft.is(dreamBlock)) output = output.or((x, y) -> (x == 15 && y == 0));

        if(!cornerUpRight.is(dreamBlock)) output = output.or((x, y) -> (x == 0 && y == 0));

        if(!corderDownLeft.is(dreamBlock)) output = output.or((x, y) -> (x == 15 && y == 15));

        if(!cornerDownRight.is(dreamBlock)) output = output.or((x, y) -> (x == 0 && y == 15));


        return output;

    }

    private record Goober(int x, int y, GooberColor color, GooberStyle style, int startTick, int endTick, boolean reverse, int lessOpacity) {
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Goober n) return effectivelyEqual(n); // Hacky but private class so ehh
            return false;
        };

        public boolean effectivelyEqual(Goober goober) {
            int difX = Math.abs(this.x - goober.x);
            int difY = Math.abs(this.y - goober.y);
            return (difX < 4 && difY < 4 || ((difX < 2 || difY < 2) && style != GooberStyle.PIXEL));
        }

        public void draw(DynamicTextureMap.DrawContext context, int animTick) {
            int col = color.color;

            switch (lessOpacity) {
                case 1: {
                    col = FastColor.ARGB32.multiply(col, 0xFE999999);
                    col = FastColor.ARGB32.multiply(col, 0xFE999999);
                }
                case 2: {
                    col = FastColor.ARGB32.multiply(col, 0xFE999999);
                    col = FastColor.ARGB32.multiply(col, 0xFE999999);
                    col = FastColor.ARGB32.multiply(col, 0xFE999999);
                }
            }

            if(x < 15 && y < 15 && x > 0 && y > 0) {
                switch(style) {
                    case PIXEL -> context.setPixelColor(x, y, col);
                    case STAR -> {
                        context.setPixelColor(x + 1, y, col);
                        context.setPixelColor(x, y + 1, col);
                        context.setPixelColor(x - 1, y, col);
                        context.setPixelColor(x, y - 1, col);
                    }
                    case STAR_ANIMATED -> {
                        context.setPixelColor(x + 1, y, col);
                        context.setPixelColor(x, y + 1, col);
                        context.setPixelColor(x - 1, y, col);
                        context.setPixelColor(x, y - 1, col);
                        if(testAnimation(animTick)) {
                            context.setPixelColorSafe(x + 2, y, col);
                            context.setPixelColorSafe(x - 2, y,  col);
                            context.setPixelColorSafe(x, y + 2, col);
                            context.setPixelColorSafe(x, y - 2, col);

                            int transCol = FastColor.ARGB32.multiply(col, 0xFE999999);
                            context.setPixelColorSafe(x + 1, y + 1, transCol);
                            context.setPixelColorSafe(x - 1, y - 1, transCol);
                            context.setPixelColorSafe(x - 1, y + 1, transCol);
                            context.setPixelColorSafe(x + 1, y - 1, transCol);
                        }
                    }
                    case THINGY -> {
                        context.setPixelColor(x + 1, y, col);
                        context.setPixelColor(x, y + 1, col);
                        context.setPixelColor(x - 1, y, col);
                        context.setPixelColor(x, y - 1, col);
                        context.setPixelColor(x + 1, y + 1, col);
                        context.setPixelColor(x - 1, y - 1, col);
                    }

                }
            } else {
                context.setPixelColor(x, y, col);
            }
        }

        private boolean testAnimation(int tick) {
            return reverse != (tick >= startTick && tick < endTick);
        }
    }

    private enum GooberColor {

        YELLOW(rgb(255, 255, 0)),
        CYAN(rgb(0, 241, 254)),
        PURPLE(rgb(66, 66, 158)),
        MAGENTA(rgb(255, 71, 231)),
        GREEN1(rgb(40, 125, 77)),
        GREEN2(rgb(0, 158, 13));

        final int color;
        GooberColor(int color) {
            this.color = color;
        }

        public int red() {
            return FastColor.ARGB32.red(color);
        }

        public int green() {
            return FastColor.ARGB32.green(color);
        }

        public int blue() {
            return FastColor.ARGB32.blue(color);
        }

        public static int rgb(int r, int g, int b) {
            return FastColor.ARGB32.color(255, b, g, r); // abgr moment
        }
    }

    private enum GooberStyle implements WeightedEntry {
        PIXEL(2), // Pixel has a lower weight because any node drawn on a border becomes a pixel
        STAR(3),
        THINGY(1),
        STAR_ANIMATED(3);

        final Weight weight;
        GooberStyle(int weight) {
            this.weight = Weight.of(weight);
        }

        @Override
        public Weight getWeight() {
            return this.weight;
        }

        private static final WeightedRandomList<GooberStyle> weightedRandomList = WeightedRandomList.create(values());

        public static GooberStyle weighted(RandomSource rng) {
            return weightedRandomList.getRandom(rng).get();
        }
    }

    public static boolean useFabulousGraphics() {
        return (Minecraft.getInstance().options.graphicsMode().get()).getId() >= GraphicsStatus.FABULOUS.getId();
    }

    private static final SimpleWeightedRandomList<Object> transparency = new SimpleWeightedRandomList.Builder<>().add(0, 5).add(1, 2).add(2, 1).build();

    public static int getTransparency(RandomSource rng) {
        return (int) transparency.getRandom(rng).get().getData();
    }
}
