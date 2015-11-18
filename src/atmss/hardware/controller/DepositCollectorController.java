/**
 *
 */
package atmss.hardware.controller;

import atmss.hardware.exceptioins.DepositCollectorException;
import atmss.hardware.exceptioins.HardwareException;
import atmss.hardware.view.DepositCollectorView;
import hwEmulators.DepositCollector;

/**
 * @author freeman, tony
 *
 */
public class DepositCollectorController extends HardwareController {

	private DepositCollectorView depositCollectorView;

	/**
	 *
	 */
	public DepositCollectorController(DepositCollector depositCollector) {
		depositCollectorView = new DepositCollectorView(depositCollector);
	}

	public boolean collectEnvelop(int timeout) throws Exception {
		try {
			return depositCollectorView.collectEnvelop(timeout);
		} catch (DepositCollectorException e) {
			HandleException(e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see atmss.hardware.HardwareController#updateStatus()
	 */
	@Override
	public boolean updateStatus() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see atmss.hardware.HardwareController#reset()
	 */
	@Override
	public boolean reset() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see atmss.hardware.HardwareController#shutdonw()
	 */
	@Override
	public boolean shutdown() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see atmss.hardware.HardwareController#HandleException(atmss.hardware.hw.exceptioins.HardwareException)
	 */
	@Override
	void HandleException(HardwareException ex) throws Exception {
		if (ex instanceof DepositCollectorException) {
			System.err.println(ex.getClass().getSimpleName() + "\n    " + ex.getExceptionCode() + ":" + ex.getMessage());
		}
	}

}
