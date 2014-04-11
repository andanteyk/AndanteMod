package andmod.CommandCard;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Gui_CommandCard extends GuiContainer {

	private GuiTextField commandTextField;
	private GuiTextField nameTextField;
	private GuiButton doneBtn;
	private GuiButton cancelBtn;
	private GuiButton regBtn;
	private GuiButton unregBtn;


	private ItemStack commandItem;


	public Gui_CommandCard( EntityPlayer eplayer, World world ) {
		super( new Gui_Container( eplayer, world, MathHelper.floor_double( eplayer.posX ), MathHelper.floor_double( eplayer.posY ), MathHelper.floor_double( eplayer.posZ ) ) );
		commandItem = eplayer.getCurrentEquippedItem();
	}

	@Override
	public void updateScreen() {
		commandTextField.updateCursorCounter();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		buttonList.clear();
        buttonList.add( doneBtn   = new GuiButton( 0, width / 2 - 100, height / 4 + 72 + 12,  I18n.func_135053_a( "gui.done" ) ) );
        buttonList.add( cancelBtn = new GuiButton( 1, width / 2 - 100, height / 4 + 96 + 12, I18n.func_135053_a( "gui.cancel" ) ) );
        buttonList.add( regBtn    = new GuiButton( 2, width / 2 - 100, height / 4 + 120 + 12, StatCollector.translateToLocal( "CommandCard.gui.register" ) ) );
        //this.buttonList.add( this.unregBtn  = new GuiButton( 3, this.width / 2 - 100, this.height / 4 + 144 + 12, I18n.func_135053_a( "Unregister" ) ) );

        commandTextField = new GuiTextField( fontRenderer, width / 2 - 150, 60, 300, 20 );
        commandTextField.setMaxStringLength( 32767 );
        commandTextField.setFocused( true );
        if ( commandItem.getTagCompound() == null )
        	Item_CommandCard.initNBT( commandItem );
        commandTextField.setText( Item_CommandCard.getCommand( commandItem ) );

        nameTextField = new GuiTextField( fontRenderer, width / 2 - 150, /*92*/108, 300, 20 );
        nameTextField.setMaxStringLength( 32767 );
        nameTextField.setFocused( false );
        nameTextField.setText( commandItem.getDisplayName() );

        //this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
	}


	@Override
	public void onGuiClosed() {
        Keyboard.enableRepeatEvents( false );
    }


	@Override
	protected void actionPerformed( GuiButton gbutton ) {

		if ( gbutton.enabled ) {

			switch ( gbutton.id ) {
			case 0:		//done

				//checkme
				setItemData( commandItem, nameTextField.getText(), commandTextField.getText() );

                mc.displayGuiScreen( (GuiScreen)null );
				break;

			case 1:		//cancel
				mc.displayGuiScreen( (GuiScreen)null );
	            break;

			case 2:		//register
				setItemData( commandItem, nameTextField.getText(), commandTextField.getText(), 1 );
				Item_CommandCard.addCommand( commandItem.getDisplayName(), Item_CommandCard.getCommand( commandItem ), Item_CommandCard.getColor( commandItem ) );

				mc.displayGuiScreen( (GuiScreen)null );
				break;

			case 3:		//unregister	//checkme: can i set the data?
				setItemData( commandItem, nameTextField.getText(), commandTextField.getText() );
				Item_CommandCard.removeCommand( commandItem.getDisplayName(), Item_CommandCard.getCommand( commandItem ) );
            	break;

			}

        }
    }


	protected void setItemData( ItemStack items, String name, String command ) {
		setItemData( items, name, command, 0 );
	}

	protected void setItemData( ItemStack items, String name, String command, int flag ) {

		//Throwing packets
		byte[] bname, bcommand;
		ByteArrayOutputStream bos;
		DataOutputStream ostream;


		try {

			bname = name.getBytes( "UTF-8" );
			bcommand = command.getBytes( "UTF-8" );

			bos = new ByteArrayOutputStream( 16 + bname.length + bcommand.length );
			ostream = new DataOutputStream( bos );

			ostream.writeInt( bname.length );
			ostream.writeInt( bcommand.length );
			ostream.writeInt( Item_CommandCard.getColor( items ) );
			ostream.writeInt( flag );
			ostream.write( bname );
			ostream.write( bcommand );


			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "AM_CC_CommandGui";
			packet.data = bos.toByteArray();
			packet.length = bos.size();

			Side side = FMLCommonHandler.instance().getEffectiveSide();
			if ( side == Side.CLIENT ) {
				EntityClientPlayerMP eplayer = mc.thePlayer;

				eplayer.sendQueue.addToSendQueue( packet );
			}


		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}


	@Override
	protected void keyTyped( char par1, int par2 ) {
        commandTextField.textboxKeyTyped( par1, par2 );
        nameTextField.textboxKeyTyped( par1, par2 );

        //this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;

        if ( par2 != 28 && par2 != 156 ) {
            if ( par2 == 1 )
                actionPerformed( cancelBtn );
        } else {
            actionPerformed( doneBtn );
        }
    }


	@Override
	protected void mouseClicked( int par1, int par2, int par3 ) {
        super.mouseClicked( par1, par2, par3 );
        commandTextField.mouseClicked( par1, par2, par3 );
        nameTextField.mouseClicked( par1, par2, par3 );
    }


	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 ) {
		//drawCenteredString( this.fontRenderer, I18n.func_135053_a( "advMode.setCommand" ), this.width / 2, 20, 16777215 );
        drawString( fontRenderer, I18n.func_135053_a( "advMode.command" ), width / 2 - 150, 47, 0xA0A0A0 );
        drawString( fontRenderer, StatCollector.translateToLocal( "CommandCard.gui.name" ), width / 2 - 150, 47 + 48, 0xA0A0A0 );
        /*/
        drawString( this.fontRenderer, I18n.func_135053_a( "advMode.nearestPlayer" ), this.width / 2 - 150, 97, 10526880 );
        drawString( this.fontRenderer, I18n.func_135053_a( "advMode.randomPlayer" ), this.width / 2 - 150, 108, 10526880 );
        drawString( this.fontRenderer, I18n.func_135053_a( "advMode.allPlayers" ), this.width / 2 - 150, 119, 10526880 );
        //*/
        commandTextField.drawTextBox();
        nameTextField.drawTextBox();
	}



	@Override
	protected void drawGuiContainerBackgroundLayer( float f, int i, int j ) {
		drawDefaultBackground();
	}


	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}


}
