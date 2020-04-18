package ryoryo.polishedstone.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.client.render.ThirdPersonCameraController;
import ryoryo.polishedstone.util.LibKey;

public class ModClientEventHandler
{
	@SubscribeEvent
	public void onKeyBinding(InputEvent.KeyInputEvent event)
	{
		//Jキーを押すとプレイヤーの向きを90度ずつで調整する
		if(LibKey.KEY_FACING_ADJUSTMENT.isPressed() && Minecraft.getMinecraft().currentScreen == null)
		{
			EntityPlayer player = Utils.getPlayer();
			float yaw = player.rotationYaw;
			for(; yaw < 0; yaw += 360f);
			int f = MathHelper.floor((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3;
			yaw = f * 90f;
			player.rotationYaw = yaw;
			player.rotationYawHead = yaw;
		}

		if(LibKey.KEY_THIRD_PERSON_CAMERA_DISTANCE.isPressed())
		{
			ThirdPersonCameraController.getInstance().turn();
		}

		//PickUpWidelyのtoggle用
		if(LibKey.KEY_PICK_UP_WIDELY_TOGGLE.isPressed())
		{
			if(EventHelper.pickUpWidelyToggle == true)
			{
				EventHelper.pickUpWidelyToggle = false;
				Utils.addChat("PickUpWidely : OFF");
				return;
			}

			if(EventHelper.pickUpWidelyToggle == false)
			{
				EventHelper.pickUpWidelyToggle = true;
				Utils.addChat("PickUpWidely : ON");
				return;
			}
		}
	}

	@SubscribeEvent
	public void onDebugOverayDraw(RenderGameOverlayEvent.Text event)
	{
		//F3画面右上にマイクラ内の経過日数、現在時刻を表示するように
		//Reference : vazkii.botania.client.core.handler.DebugHandler
		World world = Utils.getWorld();
		long totalTime = world.getTotalWorldTime();
		long time = world.getWorldTime();
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo)
		{
			event.getRight().add("");//一行開けてる
			event.getRight().add(totalTime / 24000 + " days " + String.format("%02d", (time / 1000 + 6) % 24)/*時間*/ + ":" + String.format("%02d", (time % 1000) * 60 / 1000)/*分*/);//総経過日数、現在時刻を24で
		}
	}

	//TODO
	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Post event)
	{
		EntityPlayer player = event.getEntityPlayer();
		ModelPlayer model = event.getRenderer().getMainModel();
		ItemStack stack = player.getActiveItemStack();

		if(!stack.isEmpty() && stack.getItem() == Register.ITEM_INVINCIBLE_BOW)
		{
			model.rightArmPose = ArmPose.BOW_AND_ARROW;
			model.leftArmPose = ArmPose.BOW_AND_ARROW;
			Utils.addChat(player, "wow");
		}
	}
}
