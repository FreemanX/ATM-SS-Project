/**
 *
 */
package atmss.hardware.controller;

import atmss.hardware.exceptioins.EnvelopDispenserException;
import atmss.hardware.exceptioins.HardwareException;
import atmss.hardware.view.EnvelopDispenserView;
import hwEmulators.EnvelopDispenser;
import hwEmulators.Msg;

// TODO: Auto-generated Javadoc
/**
 * The Class EnvelopDispenserController.
 *
 * @author freeman
 */
public class EnvelopDispenserController extends HardwareController {

	/** The envelop dispenser view. */
	private EnvelopDispenserView envelopDispenserView;

	/**
	 * Instantiates a new envelop dispenser controller.
	 *
	 * @param envelopDispenser the EnvelopDispenserView
	 */
	public EnvelopDispenserController(EnvelopDispenser envelopDispenser) {
		this.envelopDispenserView = new EnvelopDispenserView(envelopDispenser);
	}

	/**
	 * Eject envelop.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean ejectEnvelop() throws Exception {
		try {
			return envelopDispenserView.ejectEnvelop();
		} catch (EnvelopDispenserException e) {
			HandleException(e);
		}
		return false;
	}

	/**
	 * Gets the envelop count.
	 *
	 * @return the envelop count
	 * @throws Exception the exception
	 */
	public int getEnvelopCount() throws Exception {
		try {
			return envelopDispenserView.getEnvelopCount();
		} catch (EnvelopDispenserException e) {
			HandleException(e);
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atmss.hardware.HardwareController#updateStatus()
	 */
	@Override
	public boolean updateStatus() throws Exception {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		try {
			this.status = envelopDispenserView.checkStatus();
			this._maincontrollerMBox.send(new Msg("ED", status, "I'm OK"));
			isSuccess = true;
		} catch (HardwareException e) {
			// TODO Auto-generated catch block
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
		if (ex instanceof EnvelopDispenserException) {
			reportToMainController(ex, "ED");
		} else
			throw ex;
	}

}
