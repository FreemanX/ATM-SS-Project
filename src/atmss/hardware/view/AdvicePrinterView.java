/**
 * 
 */
package atmss.hardware.view;

import atmss.hardware.exceptioins.AdvicePrinterException;
import hwEmulators.AdvicePrinter;

/**
 * @author freeman
 *
 */
public class AdvicePrinterView extends HardwareView {
	private AdvicePrinter _advicePrinter;

	/**
	 * 
	 */
	public AdvicePrinterView(AdvicePrinter ap) {
		// TODO Auto-generated constructor stub
		this._advicePrinter = ap;
	}

	public boolean print(String[] advice) throws AdvicePrinterException {
		boolean isSuccess = false;
		for (String str : advice) {
			checkStatus();
			this._advicePrinter.println(str);
		}
		isSuccess = true;

		return isSuccess;
	}

	public int checkInventory() throws AdvicePrinterException {
		/*
		 * TODO call the hardware to do
		 */
		int res = this._advicePrinter.getResource();
		if (res < 1) {
			throwException(this._advicePrinter.getAPStatus());
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atmss.hardware.hw.Hardware#checkStatus()
	 */
	@Override
	public int checkStatus() throws AdvicePrinterException {
		
		int currStatus = this._advicePrinter.getAPStatus();
		if (currStatus % 100 != 0)
			throwException(currStatus);
		return currStatus;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atmss.hardware.hw.Hardware#reset()
	 */
	@Override
	public boolean reset() throws AdvicePrinterException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atmss.hardware.hw.Hardware#shutdown()
	 */
	@Override
	public boolean shutdown() throws AdvicePrinterException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atmss.hardware.hw.Hardware#throwException(int, java.lang.String)
	 */
	@Override
	void throwException(int Code) throws AdvicePrinterException {
		if (Code > 190)
			throw new AdvicePrinterException();
		else
			throw new AdvicePrinterException(Code);
	}

}