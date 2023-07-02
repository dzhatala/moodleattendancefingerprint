package moodle_login_01;

public class RemotePair {
	long local=-1;
	public long getLocal() {
		return local;
	}

	public void setLocal(long local) {
		this.local = local;
	}

	public long getRemote() {
		return remote;
	}

	public void setRemote(long remote) {
		this.remote = remote;
	}

	long remote=-1;
	
	public String toString(){
		return "local:"+local + ", remote:"+remote;
	}
}
