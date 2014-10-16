package Monitoramento;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Mensageiro extends UnicastRemoteObject implements InterfaceMensageiro{
	
	protected Mensageiro() throws RemoteException {
		super();
	}
	
	@Override
	public boolean desligar() throws IOException {
		Runtime.getRuntime().exec("cmd /c c:\\windows\\system32\\shutdown -s -f -t 0");
		return true;
	}

	@Override
	public byte[] getMacAdrress() throws RemoteException {
        try {

            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            byte[] mac = network.getHardwareAddress();
            return mac;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
    public byte[] getScreenShot() throws RemoteException {
        try {
            System.out.println("Tirando um screenshot...");
            Robot robot = new Robot();
            BufferedImage img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            baos.close();

            return baos.toByteArray();
        } catch (AWTException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] getPreviewShot() throws RemoteException {
        try {
            Robot robot = new Robot();
            BufferedImage img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            BufferedImage dimg = new BufferedImage(img.getWidth() / 4, img.getHeight() / 4, BufferedImage.TYPE_INT_RGB);
            Image tmp = img.getScaledInstance(dimg.getWidth(), dimg.getHeight(), Image.SCALE_SMOOTH);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(dimg, "jpg", baos);
            baos.flush();
            baos.close();

            return baos.toByteArray();
        } catch (AWTException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
