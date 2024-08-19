
package dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.flywheel.vertex;

import com.jozufozu.flywheel.api.vertex.VertexList;
import com.jozufozu.flywheel.api.vertex.VertexType;
import com.jozufozu.flywheel.core.Formats;
import com.jozufozu.flywheel.core.model.*;
import com.jozufozu.flywheel.core.vertex.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class BasicModel implements Model {

    private final String name;
    private final VertexList reader;
    private final int vertexCount;

    public BasicModel(List<Quad> input, String name) {
        this.name = name;
        this.vertexCount = input.size() * 4;

        ByteBuffer buffer = MemoryUtil.memAlloc(size());
        PosTexNormalWriterUnsafe writer = Formats.POS_TEX_NORMAL.createWriter(buffer);

        for(Quad quad : input) {
            quad.buffer(writer);
        }

        this.reader = writer.intoReader();
        MemoryUtil.memFree(buffer);
    }


    /**
     * Create a new model builder
     * @param name the new model's name
     * @return the newly created builder
     */
    public static Builder builder(String name) {
        return new Builder(name);
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull VertexList getReader() {
        return reader;
    }

    @Override
    public int vertexCount() {
        return vertexCount;
    }

    @Override
    public @NotNull VertexType getType() {
        return Formats.POS_TEX_NORMAL;
    }

    @Override
    public void delete() {
        reader.delete();
    }

    public static class Builder {
        private final String name;
        private final List<Quad> quads = new ArrayList<>();
        private TextureAtlasSprite sprite = null;
        Builder(String name) {
            this.name = name;
        }

        /**
         * Set the {@link TextureAtlasSprite} to be used for the next quad
         * @param sprite
         * @return
         */
        public Builder sprite(TextureAtlasSprite sprite) {
            this.sprite = sprite;
            return this;
        }

        /**
         * Start a new quad
         * @return quad builder
         */
        public Quad quad() {
            Quad quad = new Quad(this, sprite);
            this.sprite = null;
            return quad;
        }

        public void appendQuad(Quad quad) {
            quads.add(quad);
        }

        /**
         * Complete the builder and create a new {@link BasicModel}
         * This buffers a all quads inta a {@link VertexList}, and after this {@link Quad} instances aren't accessible
         * and existing quads are no longer writable, you can read the quads from the model using {@link BasicModel.read()}
         * @return the built model
         */
        public BasicModel build() {
            return new BasicModel(quads, name);
        }
    }

    public static class Quad {
        public static final VertexType FORMAT = Formats.POS_TEX_NORMAL;
        public static final int VERTEX_STRIDE = 6;
        public static final int X_OFFSET = 0;
        public static final int Y_OFFSET = 1;
        public static final int Z_OFFSET = 2;
        public static final int U_OFFSET = 3;
        public static final int V_OFFSET = 4;
        public static final int NORMAL_OFFSET = 5;
        private static final float NORMAL_PACK = 127.0f;
        private static final float NORMAL_UNPACK = 1 / NORMAL_PACK;
        private final int[] data;
        private final Builder parent;
        private final TextureAtlasSprite sprite;
        private int vertex = 0;
        private boolean flipU = false;
        private boolean flipV = false;

        Quad(Builder parent, @Nullable TextureAtlasSprite sprite) {
            this.data = new int[4 * VERTEX_STRIDE];
            this.parent = parent;
            this.sprite = sprite;
        }

        public Quad pos(float x, float y, float z) {
            int pos = vertex * VERTEX_STRIDE;

            data[pos + X_OFFSET] = Float.floatToIntBits(x);
            data[pos + Y_OFFSET] = Float.floatToIntBits(y);
            data[pos + Z_OFFSET] = Float.floatToIntBits(z);
            return this;
        }

        public Quad uv(float u, float v) {
            int pos = vertex * VERTEX_STRIDE;

            data[pos + U_OFFSET] = Float.floatToIntBits(u);
            data[pos + V_OFFSET] = Float.floatToIntBits(v);
            return this;
        }

        public Quad normal(float nX, float nY, float nZ) {
            return normal(packNormal(nX, nY, nZ));
        }

        public Quad normal(int packedNormal) {
            data[vertex * VERTEX_STRIDE + NORMAL_OFFSET] = packedNormal;
            return this;
        }

        public Quad next() {
            vertex++;
            if(vertex > 4) throw new IllegalStateException("Vertices have already been filled!");
            return this;
        }

        public Quad vertex(int index) {
            if(index < 0 || index >= 4) throw new IllegalArgumentException();
            vertex = index;
            return this;
        }

        public Quad putVertex(float x, float y, float z, float u, float v, Vector3f normal) {
            return pos(x, y, z).uv(u, v).normal(normal.x, normal.y, normal.z).next();
        }

        public Builder end() {
            if(vertex < 4 - 1) throw new IllegalStateException("Not all vertices filled");
            parent.appendQuad(this);
            return parent;
        }

        public Quad flipU() {
            flipU = true;
            return this;
        }

        public Quad flipV() {
            flipV = true;
            return this;
        }

        protected void buffer(PosTexNormalWriterUnsafe writer) {
            for(int vert = 0; vert < 4; vert++) {
                int pos = vert * VERTEX_STRIDE;

                float x = Float.intBitsToFloat(data[pos + X_OFFSET]);
                float y = Float.intBitsToFloat(data[pos + Y_OFFSET]);
                float z = Float.intBitsToFloat(data[pos + Z_OFFSET]);

                float u = Float.intBitsToFloat(data[pos + U_OFFSET]);
                float v = Float.intBitsToFloat(data[pos + V_OFFSET]);

                if(flipU) u = 1f - u;
                if(flipV) v = 1f - v;

                if(sprite != null) {
                    u = sprite.getU(u * 16f);
                    v = sprite.getV(v * 16f);
                }

                int normal = data[pos + NORMAL_OFFSET];

                writer.putVertex(x, y, z, unpackNX(normal), unpackNY(normal), unpackNZ(normal), u, v);
            }
        }

        public BakedQuad toVanillaQuad(int tintIndex, Direction face, boolean shade) {
            if(sprite == null) throw new IllegalArgumentException("Cannot convert to vanilla quad without sprite");
            return new BakedQuad(data, tintIndex, face, sprite, shade);
        }

        private static int packNormal(float x, float y, float z) {
            x = Mth.clamp(x, -1, 1);
            y = Mth.clamp(y, -1, 1);
            z = Mth.clamp(z, -1, 1);

            return ((int) (x * NORMAL_PACK) & 0xFF) | (((int) (y * NORMAL_PACK) & 0xFF) << 8) | (((int) (z * NORMAL_PACK) & 0xFF) << 16);
        }

        public static float unpackNX(int packedNormal) {
            return ((byte) (packedNormal & 0xFF)) * NORMAL_UNPACK;
        }

        public static float unpackNY(int packedNormal) {
            return ((byte) ((packedNormal >>> 8) & 0xFF)) * NORMAL_UNPACK;
        }

        public static float unpackNZ(int packedNormal) {
            return ((byte) ((packedNormal >>> 16) & 0xFF)) * NORMAL_UNPACK;
        }

    }
}

