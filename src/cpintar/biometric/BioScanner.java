package cpintar.biometric;

public interface BioScanner {
	
	void addBioScanListener(BioScanListener l);
	void removeBioScanListener(BioScanListener l);
	void removeAllBioScanListener();
}
