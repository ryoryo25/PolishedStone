package ryoryo.polishedstone.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.client.model.baked.BakedModelFullbright;
import ryoryo.polishedstone.client.render.ThirdPersonCameraController;
import ryoryo.polishedstone.util.LibKey;
import ryoryo.polishedstone.util.References;

public class ModClientEventHandler {
	@SubscribeEvent
	public void onKeyBinding(InputEvent.KeyInputEvent event) {
		// Jキーを押すとプレイヤーの向きを90度ずつで調整する
		if(LibKey.KEY_FACING_ADJUSTMENT.isPressed() && Minecraft.getMinecraft().currentScreen == null) {
			EntityPlayer player = Utils.getPlayer();
			float yaw = player.rotationYaw;
			for(; yaw < 0; yaw += 360f);
			int f = MathHelper.floor((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3;
			yaw = f * 90f;
			player.rotationYaw = yaw;
			player.rotationYawHead = yaw;
		}

		if(LibKey.KEY_THIRD_PERSON_CAMERA_DISTANCE.isPressed()) {
			ThirdPersonCameraController.getInstance().turn();
		}

		// PickUpWidelyのtoggle用
		if(LibKey.KEY_PICK_UP_WIDELY_TOGGLE.isPressed()) {
			if(EventHelper.pickUpWidelyToggle == true) {
				EventHelper.pickUpWidelyToggle = false;
				Utils.sendChat("PickUpWidely : OFF");
				return;
			}

			if(EventHelper.pickUpWidelyToggle == false) {
				EventHelper.pickUpWidelyToggle = true;
				Utils.sendChat("PickUpWidely : ON");
				return;
			}
		}
	}

	@SubscribeEvent
	public void onDebugOverayDraw(RenderGameOverlayEvent.Text event) {
		// F3画面右上にマイクラ内の経過日数、現在時刻を表示するように
		// Reference : vazkii.botania.client.core.handler.DebugHandler
		World world = Utils.getWorld();
		long totalTime = world.getTotalWorldTime();
		long time = world.getWorldTime();
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			event.getRight().add("");// 一行開けてる
			event.getRight().add(totalTime / 24000 + " days " + String.format("%02d", (time / 1000 + 6) % 24)/* 時間 */ + ":" + String.format("%02d", (time % 1000) * 60 / 1000)/* 分 */);// 総経過日数、現在時刻を24で
		}
	}

	// TODO
	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Post event) {
		EntityPlayer player = event.getEntityPlayer();
		ModelPlayer model = event.getRenderer().getMainModel();
		ItemStack stack = player.getActiveItemStack();

		if(!stack.isEmpty() && stack.getItem() == Register.ITEM_INVINCIBLE_BOW) {
			model.rightArmPose = ArmPose.BOW_AND_ARROW;
			model.leftArmPose = ArmPose.BOW_AND_ARROW;
			Utils.sendChat(player, "wow");
		}
	}

	//long creative block pick
	@SubscribeEvent
	public void onPickBlockMiddleClick(InputEvent.MouseInputEvent event) {
		if(Minecraft.getMinecraft().gameSettings.keyBindPickBlock.isPressed()) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayer player = mc.player;
			World world = mc.world;
			RayTraceResult ray = rayTraceLiquid(64.0, world, player, false);

			if(player != null && player.capabilities.isCreativeMode && ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK)
				ForgeHooks.onPickBlock(ray, player, world);
		}
	}

	private static RayTraceResult rayTraceLiquid(double reachDistance, World world, EntityPlayer player, boolean useLiquids) {
		float f = player.rotationPitch;
		float f1 = player.rotationYaw;
		double d0 = player.posX;
		double d1 = player.posY + (double) player.getEyeHeight();
		double d2 = player.posZ;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = reachDistance;
		Vec3d vec3d1 = vec3d.addVector((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
		return world.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	}

	public static class PreInit {
		@SubscribeEvent
		public void onBakeModel(ModelBakeEvent event) {
			PSV2Core.LOGGER.info("Baking model");
			for(ModelResourceLocation resource : event.getModelRegistry().getKeys()) {
				ResourceLocation key = new ResourceLocation(resource.getResourceDomain(), resource.getResourcePath());

				if(Register.BLOCK_CHROMA_KEY_BACK.getRegistryName().toString().equals(key.toString())) {
					String path = References.MOD_ID + ":blocks/chroma_key_back_" + resource.getVariant().substring(6);
					event.getModelRegistry().putObject(resource, new BakedModelFullbright(event.getModelRegistry().getObject(resource), path));
				}
			}
		}
	}
}
