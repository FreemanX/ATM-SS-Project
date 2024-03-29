/**
 *
 */
package atmss.hardware.controller;

import atmss.hardware.exceptioins.DepositCollectorException;
import atmss.hardware.exceptioins.HardwareException;
import atmss.hardware.view.DepositCollectorView;
import hwEmulators.DepositCollector;
import hwEmulators.Msg;

// TODO: Auto-generated Javadoc
/**
 * The Class DepositCollectorController.
 *
 * @author freeman, tony
 */
public class DepositCollectorController extends HardwareController {

	/** The deposit collector view. */
	private DepositCollectorView depositCollectorView;

	/**
	 * Instantiates a new deposit collector controller.
	 *
	 * @param depositCollector the DepositCollectorView
	 */
	public DepositCollectorController(DepositCollector depositCollector) {
		depositCollectorView = new DepositCollectorView(depositCollector);
	}

	/**
	 * Collect envelop.
	 *
	 * @param timeout the timeout
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean collectEnvelop(int timeout) throws Exception {
		try {
			return depositCollectorView.collectEnvelop(timeout);
		} catch (DepositCollectorException e) {
			HandleException(e);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atmss.hardware.HardwareController#updateStatus()
	 */
	@Override
	public boolean updateStatus() throws Exception {
		boolean isSuccess = false;
		try {
			this.status = depositCollectorView.checkStatus();
			this._maincontrollerMBox.send(new Msg("DC", status, "I'm OK"));
			isSuccess = true;
		} catch (HardwareException e) {
			isSuccess = false;
			HandleException(e);
		}

		return isSuccess;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atmss.hardware.HardwareController#reset()
	 */
	@Override
	public boolean reset() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atmss.hardware.HardwareController#shutdonw()
	 */
	@Override
	public boolean shutdown() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atmss.hardware.HardwareController#HandleException(atmss.hardware.hw.
	 * exceptioins.HardwareException)
	 */
	@Override
	void HandleException(HardwareException ex) throws Exception {
		if (ex instanceof DepositCollectorException) {
			reportToMainController(ex, "DC");
		} else
			throw ex;
	}

}
