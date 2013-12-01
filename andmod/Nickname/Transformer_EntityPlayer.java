package andmod.Nickname;

import java.util.List;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class Transformer_EntityPlayer implements IClassTransformer, Opcodes {

	private static final String TARGET_NAME = "net.minecraft.entity.player.EntityPlayer";
	
	public Transformer_EntityPlayer() {
	}

	@Override
	public byte[] transform( String name, String transformedName, byte[] bytes ) {
		
		if ( !name.equals( TARGET_NAME ) )
			return bytes;
		
		
		// if ( FMLLaunchHandler.side().equals( "CLIENT" ) ) 
		{
			
			try {
				
				return transformEntityPlayer( bytes );
				
			} catch ( Exception e ) {
				e.printStackTrace();
			}
			
		}
		
		return null; //checkme
	}
	
	
	private byte[] transformEntityPlayer( byte[] bytes ) {
		
		ClassNode cnode = new ClassNode();
		ClassReader creader = new ClassReader( bytes );
		creader.accept( cnode, 0 );
		
		String targetMethodName = "getTranslatedEntityName";
		String targetMethodDesc = Type.getMethodDescriptor( Type.getType( String.class ) );
		
		FMLDeobfuscatingRemapper mapper = FMLDeobfuscatingRemapper.INSTANCE;
		
		for ( MethodNode mnode : (List<MethodNode>)cnode.methods ) {
			if ( targetMethodName.equals( mapper.mapMethodName( cnode.name, mnode.name, mnode.desc ) ) && targetMethodDesc.equals( mapper.mapMethodDesc( mnode.desc ) ) ) {
				
				/*/
				for ( LocalVariableNode lnode : (List<LocalVariableNode>)mnode.localVariables ) {
					if ( lnode.name.equals( "this" ) ) {
						
						InsnList overrideList = new InsnList();
						overrideList.add( new VarInsnNode( ALOAD, lnode.index ) );
						overrideList.add( new MethodInsnNode( INVOKESTATIC, "classname", "methodname", targetMethodDesc ) );
					
						mnode.instructions.insert( mnode.instructions.get( 1 ), overrideList );
					
						ClassWriter cw = new ClassWriter( ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS );
						cnode.accept( cw );
						bytes = cw.toByteArray();
						
					}
				}
				//*/
				
				
				/*/
				for ( AbstractInsnNode anode : mnode.instructions.toArray() ) {
					if ( anode instanceof VarInsnNode ) {
						
						VarInsnNode vnode = (VarInsnNode)anode;
						
						if ( vnode.getOpcode() == ILOAD && vnode.var == 9 ) {
							mnode.instructions.remove( vnode.getNext() );
							mnode.instructions.remove( vnode );
						}
					}
				}
				
				//*/
			}
		}
		
		
		return bytes;
	}


}
