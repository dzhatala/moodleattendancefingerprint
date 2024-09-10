package cpintar.biometric.zkteco;

import javax.swing.tree.DefaultMutableTreeNode;

import config.AccountConfig;

/**
 * Directory based Finger template (and images)
 * 
 * @author ZH
 * 
 */
public class DirectoryContext extends DefaultMutableTreeNode {
	String working_dir = null;

	static DirectoryContext instance=null;
	private DirectoryContext(){
		
	}
	public static void main(String args[]) {
		DirectoryContext ctx = DirectoryContext.getInstance();
//		ctx.working_dir=System.getProperty("user.dir");
		util.Logger.log("context working dir: "+ctx.working_dir);
		util.Logger.log("context image_dir: "+ctx.getImagesDirectory());
		util.Logger.log("context image_dir: "+ctx.getMasterDirectory());
	}

	/**
	 * 
	 * @return
	 */
	public String getMasterDirectory() {
		return working_dir + "/" + AccountConfig.getUsername() + "/zkteco/master";
	}

	/**
	 * enrolled images
	 * 
	 * @return
	 */
	public String getImagesDirectory() {
		return working_dir + "/" + AccountConfig.getUsername() + "/zkteco/images";
	}
	
	public static DirectoryContext getInstance(){
		if(instance==null){
			instance=new DirectoryContext();
		}
		instance.working_dir=System.getProperty("user.dir")+"/javaws";
		return instance;
	}
}
