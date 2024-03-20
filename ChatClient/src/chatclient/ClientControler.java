package chatclient;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientControler implements ActionListener{

	private ClientGiaoDien clientGiaoDien;
	public ClientControler(ClientGiaoDien clientGiaoDien) {

		this.clientGiaoDien = clientGiaoDien;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String suKien = e.getActionCommand();
		if (suKien.equals("Dang nhap")) {
			

		} else if (suKien.equals("Register")) {
			
			clientGiaoDien.dangKi();
		}else if (suKien.equals("Return")) {
			
			clientGiaoDien.dangNhap();
		}else if (suKien.equals("Login")) {
			
			clientGiaoDien.xuLyDangNhap();
		}else if (suKien.equals("Save")) {
			clientGiaoDien.xuLyDangKi();
			//clientGiaoDien.xuLyDangKi();
		}else if (suKien.equals("GỬI")) {
			clientGiaoDien.guiTinNhan();
		}else if (suKien.equals("CHỌN")) {
			clientGiaoDien.layTinNhanTuCsdl();
		}
                else if (suKien.equals("TẠO NHÓM CHAT")) {
			clientGiaoDien.createGroupChat();
		}
		
		
		
		
	}

}
