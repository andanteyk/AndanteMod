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

	public void updateScreen() {
		this.commandTextField.updateCursorCounter();
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
        
		this.buttonList.clear();
        this.buttonList.add( this.doneBtn   = new GuiButton( 0, this.width / 2 - 100, this.height / 4 + 72 + 12,  I18n.func_135053_a( "gui.done" ) ) );
        this.buttonList.add( this.cancelBtn = new GuiButton( 1, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.func_135053_a( "gui.cancel" ) ) );
        this.buttonList.add( this.regBtn    = new GuiButton( 2, this.width / 2 - 100, this.height / 4 + 120 + 12, StatCollector.translateToLocal( "CommandCard.gui.register" ) ) );
        //this.buttonList.add( this.unregBtn  = new GuiButton( 3, this.width / 2 - 100, this.height / 4 + 144 + 12, I18n.func_135053_a( "Unregister" ) ) );
        
        this.commandTextField = new GuiTextField( this.fontRenderer, this.width / 2 - 150, 60, 300, 20 );
        this.commandTextField.setMaxStringLength( 32767 );
        this.commandTextField.setFocused( true );
        if ( this.commandItem.getTagCompound() == null )
        	Item_CommandCard.initNBT( commandItem );
        this.commandTextField.setText( Item_CommandCard.getCommand( this.commandItem ) );
        
        this.nameTextField = new GuiTextField( this.fontRenderer, this.width / 2 - 150, /*92*/108, 300, 20 );
        this.nameTextField.setMaxStringLength( 32767 );
        this.nameTextField.setFocused( false );
        this.nameTextField.setText( this.commandItem.getDisplayName() );
        
        //this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
	}
	
	
	public void onGuiClosed() {
        Keyboard.enableRepeatEvents( false );
    }

	
	protected void actionPerformed( GuiButton gbutton ) {
        
		if ( gbutton.enabled ) {
			
			switch ( gbutton.id ) {
			case 0:		//done
				
				//checkme
				setItemData( commandItem, nameTextField.getText(), commandTextField.getText() );

                this.mc.displayGuiScreen( (GuiScreen)null );
				break;
				
			case 1:		//cancel
				this.mc.displayGuiScreen( (GuiScreen)null );
	            break;
	            
			case 2:		//register
				setItemData( commandItem, nameTextField.getText(), commandTextField.getText(), 1 );
				Item_CommandCard.addCommand( commandItem.getDisplayName(), Item_CommandCard.getCommand( commandItem ), Item_CommandCard.getColor( commandItem ) );
				
				this.mc.displayGuiScreen( (GuiScreen)null );
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
			
			bos = new ByteArrayOutputStream( 8 + bname.length + bcommand.length );
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
				EntityClientPlayerMP eplayer = (EntityClientPlayerMP) mc.thePlayer;
				
				eplayer.sendQueue.addToSendQueue( packet );
			}
			
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	
	protected void keyTyped( char par1, int par2 ) {
        this.commandTextField.textboxKeyTyped( par1, par2 );
        this.nameTextField.textboxKeyTyped( par1, par2 );
        
        //this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;

        if ( par2 != 28 && par2 != 156 ) {
            if ( par2 == 1 )
                this.actionPerformed( this.cancelBtn );
        } else {
            this.actionPerformed( this.doneBtn );
        }
    }
	
	 
	protected void mouseClicked( int par1, int par2, int par3 ) {
        super.mouseClicked( par1, par2, par3 );
        this.commandTextField.mouseClicked( par1, par2, par3 );
        this.nameTextField.mouseClicked( par1, par2, par3 );
    }
	
	
	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 ) {
		//this.drawCenteredString( this.fontRenderer, I18n.func_135053_a( "advMode.setCommand" ), this.width / 2, 20, 16777215 );
        this.drawString( this.fontRenderer, I18n.func_135053_a( "advMode.command" ), this.width / 2 - 150, 47, 0xA0A0A0 );
        this.drawString( this.fontRenderer, StatCollector.translateToLocal( "CommandCard.gui.name" ), this.width / 2 - 150, 47 + 48, 0xA0A0A0 );
        /*/
        this.drawString( this.fontRenderer, I18n.func_135053_a( "advMode.nearestPlayer" ), this.width / 2 - 150, 97, 10526880 );
        this.drawString( this.fontRenderer, I18n.func_135053_a( "advMode.randomPlayer" ), this.width / 2 - 150, 108, 10526880 );
        this.drawString( this.fontRenderer, I18n.func_135053_a( "advMode.allPlayers" ), this.width / 2 - 150, 119, 10526880 );
        //*/
        this.commandTextField.drawTextBox();
        this.nameTextField.drawTextBox();
	}

	
	
	@Override
	protected void drawGuiContainerBackgroundLayer( float f, int i, int j ) {
		this.drawDefaultBackground();
	}

	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	
}
