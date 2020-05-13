package ryoryo.polishedstone.client.model.baked;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;

public class BakedModelFullbright implements IBakedModel {

	//References:
	//https://www.minecraftforge.net/forum/topic/66005-how-do-i-make-a-tileentityspecialrenderer-solved-with-ibakedmodel/?tab=comments#comment-315708
	//https://github.com/raoulvdberge/refinedstorage/blob/mc1.12/src/main/java/com/raoulvdberge/refinedstorage/render/model/baked/BakedModelFullbright.java#L112

	private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

	private IBakedModel base;
	private Set<String> textures;

	public BakedModelFullbright(IBakedModel base, String... textures) {
		this.base = base;
		this.textures = Sets.newHashSet(textures);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if(state == null)
			return base.getQuads(state, side, rand);

		return transformQuads(base.getQuads(state, side, 0), textures);
	}

	private static List<BakedQuad> transformQuads(List<BakedQuad> oldQuads, Set<String> textures) {
		List<BakedQuad> quads = Lists.newArrayList(oldQuads);

		for(int i = 0; i < quads.size(); i++) {
			BakedQuad quad = quads.get(i);

			if(textures.contains(quad.getSprite().getIconName())) {
				quads.set(i, transformQuad(quad, 0.007F));
			}
		}

		return quads;
	}

	private static BakedQuad transformQuad(BakedQuad quad, float light) {
		if(isLightMapDisabled())
			return quad;

		VertexFormat newFormat = getFormatWithLightMap(quad.getFormat());

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(newFormat);

		VertexLighterFlat trans = new VertexLighterFlat(Minecraft.getMinecraft().getBlockColors()) {
			@Override
			protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z) {
				lightmap[0] = light;
				lightmap[1] = light;
			}

			@Override
			public void setQuadTint(int tint) {
				// NO OP
			}
		};

		trans.setParent(builder);

		quad.pipe(trans);

		builder.setQuadTint(quad.getTintIndex());
		builder.setQuadOrientation(quad.getFace());
		builder.setTexture(quad.getSprite());
		builder.setApplyDiffuseLighting(false);

		return builder.build();
	}

	private static boolean isLightMapDisabled() {
		return FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled;
	}

	private static VertexFormat getFormatWithLightMap(VertexFormat format) {
		if(isLightMapDisabled())
			return format;

		if(format == DefaultVertexFormats.BLOCK)
			return DefaultVertexFormats.BLOCK;
		else if(format == DefaultVertexFormats.ITEM)
			return ITEM_FORMAT_WITH_LIGHTMAP;
		else if(!format.hasUvOffset(1)) {
			VertexFormat result = new VertexFormat(format);

			result.addElement(DefaultVertexFormats.TEX_2S);

			return result;
		}

		return format;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isAmbientOcclusion(IBlockState state) {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return base.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return base.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return base.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return base.getOverrides();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return base.getItemCameraTransforms();
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return base.handlePerspective(cameraTransformType);
	}
}