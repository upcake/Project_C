package com.csslect.app.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.csslect.app.command.ACommand;
import com.csslect.app.command.ADeleteMultiCommand;
import com.csslect.app.command.AInsertMultiCommand;
import com.csslect.app.command.AJoinCommand;
import com.csslect.app.command.ALoginCommand;
import com.csslect.app.command.ASelectMultiCommand;
import com.csslect.app.command.AUpdateMultiCommand;
import com.csslect.app.command.AUpdateMultiNoCommand;
import com.csslect.app.command.AlarmDeleteMultiCommand;
import com.csslect.app.command.AlarmInsertCommand;
import com.csslect.app.command.AlarmSelectMultiCommand;
import com.csslect.app.command.AlarmUpdateMultiCommand;
import com.csslect.app.command.InfoSelectMultiCommand;
import com.csslect.app.command.AEmailChkCommand;
import com.csslect.app.command.ANickNameChkCommand;
import com.csslect.app.command.ANaverJoinCommand;
import com.csslect.app.command.ANaverLoginCommand;
import com.csslect.app.command.AKakaoJoinCommand;

import com.csslect.app.command.AKakaoLoginCommand;

@Controller
public class AController {

	ACommand command;
	
	//오토메딕 로그인
	@RequestMapping(value = "/anLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anLogin(HttpServletRequest req, Model model) {
		System.out.println("anLogin()");
		System.out.println("!!!!");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String member_email = (String) req.getParameter("member_email");
		String member_password = (String) req.getParameter("member_password");

		System.out.println(member_email);
		System.out.println(member_password);

		model.addAttribute("member_email", member_email);
		model.addAttribute("member_password", member_password);

		command = new ALoginCommand();
		command.execute(model);

		return "anLogin";
	}
	
	//네이버 로그인
		@RequestMapping(value = "/anNaverLogin", method = { RequestMethod.GET, RequestMethod.POST })
		public String anNaverLogin(HttpServletRequest req, Model model) {
			System.out.println("anNaverLogin()");
			try {
				req.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String naver_email = (String) req.getParameter("naver_email");
			String naver_nickname = (String) req.getParameter("naver_nickname");

			System.out.println(naver_email);
			System.out.println(naver_nickname);

			model.addAttribute("naver_email", naver_email);
			model.addAttribute("naver_nickname",naver_nickname);

			command = new ANaverLoginCommand();
			command.execute(model);

			return "anNaverLogin";
		}
	
	//네이버 회원가입
	@RequestMapping(value = "/anNaverJoin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anNaverJoin(HttpServletRequest req, Model model) {
		System.out.println("anNaverJoin()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String naver_email = (String) req.getParameter("naver_email");
		String naver_nickname = (String) req.getParameter("naver_nickname");
		

		System.out.println(naver_email);
		System.out.println(naver_nickname);

		model.addAttribute("naver_email", naver_email);
		model.addAttribute("naver_nickname", naver_nickname);

		command = new ANaverJoinCommand();
		command.execute(model);

		return "anNaverJoin";
	}
	
	//카카오 로그인
	@RequestMapping(value = "/anKakaoLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anKakaoLogin(HttpServletRequest req, Model model) {
		System.out.println("anKakaoLogin()");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String kakao_email = (String) req.getParameter("kakao_email");
		String kakao_nickname = (String) req.getParameter("kakao_nickname");

		System.out.println(kakao_email);
		System.out.println(kakao_nickname);

		model.addAttribute("kakao_email", kakao_email);
		model.addAttribute("kakao_nickname", kakao_nickname);

		command = new AKakaoLoginCommand();
		command.execute(model);
		
		return "anKakaoLogin";
	}
	
	//카카오 회원가입
	@RequestMapping(value = "/anKakaoJoin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anKakaoJoin(HttpServletRequest req, Model model) {
		System.out.println("anKakaoJoin()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String kakao_email = (String) req.getParameter("kakao_email");
		String kakao_nickname = (String) req.getParameter("kakao_nickname");
		

		System.out.println(kakao_email);
		System.out.println(kakao_nickname);

		model.addAttribute("kakao_email", kakao_email);
		model.addAttribute("kakao_nickname", kakao_nickname);

		command = new AKakaoJoinCommand();
		command.execute(model);

		return "anKakaoJoin";
	}

	@RequestMapping(value = "/anJoin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anJoin(HttpServletRequest req, Model model) {
		System.out.println("anJoin()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_email = (String) req.getParameter("member_email");
		String member_password = (String) req.getParameter("member_password");
		String member_nickname = (String) req.getParameter("member_nickname");
		String member_phonenum = (String) req.getParameter("member_phonenum");

		System.out.println(member_email);
		System.out.println(member_password);
		System.out.println(member_nickname);
		System.out.println(member_phonenum);

		model.addAttribute("member_email", member_email);
		model.addAttribute("member_password", member_password);
		model.addAttribute("member_nickname", member_nickname);
		model.addAttribute("member_phonenum", member_phonenum);

		command = new AJoinCommand();
		command.execute(model);

		return "anJoin";
	}

	@RequestMapping(value = "/anEmailChk", method = { RequestMethod.GET, RequestMethod.POST })
	public String anEmailChk(HttpServletRequest req, Model model) {
		System.out.println("anEmailChk()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_email = (String) req.getParameter("member_email");

		System.out.println(member_email);

		model.addAttribute("member_email", member_email);

		command = new AEmailChkCommand();
		command.execute(model);

		return "anEmailChk";

	}

	@RequestMapping(value = "/anNickNameChk", method = { RequestMethod.GET, RequestMethod.POST })
	public String anNickNameChk(HttpServletRequest req, Model model) {
		System.out.println("anNickNameChk()");
		
		try { 
			req.setCharacterEncoding("UTF-8"); 
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace(); 
		}
		

		String member_nickname = (String) req.getParameter("member_nickname");

		System.out.println(member_nickname);

		model.addAttribute("member_nickname", member_nickname);

		command = new ANickNameChkCommand();
		command.execute(model);

		return "anNickNameChk";

	}

	@RequestMapping(value = "/anSelectMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public String anSelectMulti(HttpServletRequest req, Model model) {
		System.out.println("anSelectMulti()");

		command = new ASelectMultiCommand();
		command.execute(model);

		return "anSelectMulti";
	}

	@RequestMapping(value = "/anInsertMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public String anInsertMulti(HttpServletRequest req, Model model) {
		System.out.println("anInsertMulti()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String dbImgPath = (String) req.getParameter("dbImgPath");

		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println(dbImgPath);

		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		model.addAttribute("dbImgPath", dbImgPath);

		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = multi.getFile("image");

		if (file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);

			// 디렉토리 존재하지 않으면 생성
			makeDir(req);

			if (file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/");

				System.out.println(fileName + " : " + realImgPath);
				System.out.println("fileSize : " + file.getSize());

				try {
					// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				// 이미지파일 실패시
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);

			}

		}

		command = new AInsertMultiCommand();
		command.execute(model);

		return "anInsertMulti";
	}

	public void makeDir(HttpServletRequest req) {
		File f = new File(req.getSession().getServletContext().getRealPath("/resources"));
		if (!f.isDirectory()) {
			f.mkdir();
		}
	}

	@RequestMapping(value = "/anUpdateMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public void anUpdateMulti(HttpServletRequest req, Model model) {
		System.out.println("anUpdateMulti()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		String pDbImgPath = (String) req.getParameter("pDbImgPath");

		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println("Sub1Update:dbImgPath " + dbImgPath);
		System.out.println("Sub1Update:pDbImgPath " + pDbImgPath);

		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		model.addAttribute("dbImgPath", dbImgPath);

		// 이미지가 서로 같으면 삭제하지 않고 다르면 기존이미지 삭제
		if (!dbImgPath.equals(pDbImgPath)) {

			String pFileName = req.getParameter("pDbImgPath")
					.split("/")[req.getParameter("pDbImgPath").split("/").length - 1];
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);

			File delfile = new File(delDbImgPath);
			System.out.println(delfile.getAbsolutePath());

			if (delfile.exists()) {
				boolean deleteFile = false;
				while (deleteFile != true) {
					deleteFile = delfile.delete();
				}

			} // if(delfile.exists())

		} // if(!dbImgPath.equals(pDbImgPath))

		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = null;

		file = multi.getFile("image");

		if (file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);

			// 디렉토리 존재하지 않으면 생성
			makeDir(req);

			if (file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/");

				System.out.println(fileName + " : " + realImgPath);
				System.out.println("fileSize : " + file.getSize());

				try {
					// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);

			}

		}

		command = new AUpdateMultiCommand();
		command.execute(model);

	}

	@RequestMapping(value = "/anUpdateMultiNo", method = { RequestMethod.GET, RequestMethod.POST })
	public void anUpdateMultiNo(HttpServletRequest req, Model model) {
		System.out.println("anUpdateMultiNo()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");

		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);

		command = new AUpdateMultiNoCommand();
		command.execute(model);

	}

	@RequestMapping(value = "/anDeleteMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public void anDeleteMulti(HttpServletRequest req, Model model) {
		System.out.println("anDeleteMulti()");

		model.addAttribute("id", req.getParameter("id"));

		System.out.println((String) req.getParameter("id"));
		System.out.println((String) req.getParameter("delDbImgPath"));

		String pFileName = req.getParameter("delDbImgPath")
				.split("/")[req.getParameter("delDbImgPath").split("/").length - 1];
		String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);

		// 이미지 파일지우기
		File delfile = new File(delDbImgPath);
		System.out.println(delfile.getAbsolutePath());

		if (delfile.exists()) {
			System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
			boolean deleteFile = false;
			while (deleteFile != true) {
				deleteFile = delfile.delete();
			}
		}

		command = new ADeleteMultiCommand();
		command.execute(model);
	}

	@RequestMapping(value="/alarmInsert", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String alarmInsert(HttpServletRequest req, Model model){
		System.out.println("alarmInsert()");
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 		
		
		String alarm_Email = (String) req.getParameter("alarm_Email");
		String alarm_Id= (String) req.getParameter("alarm_Id");
		String alarm_Title = (String) req.getParameter("alarm_Title");
		String alarm_Sunday = (String) req.getParameter("alarm_Sunday");
		String alarm_Monday = (String) req.getParameter("alarm_Monday");
		String alarm_Tuesday = (String) req.getParameter("alarm_Tuesday");
		
		String alarm_Wednesday = (String) req.getParameter("alarm_Wednesday");
		String alarm_Thursday = (String) req.getParameter("alarm_Thursday");
		String alarm_Friday = (String) req.getParameter("alarm_Friday");
		String alarm_Saturday = (String) req.getParameter("alarm_Saturday");
		String alarm_Times = (String) req.getParameter("alarm_Times");
		
		String alarm_Ringtime1_Hour = (String) req.getParameter("alarm_Ringtime1_Hour");
		String alarm_Ringtime1_Minute = (String) req.getParameter("alarm_Ringtime1_Minute");
		String alarm_Ringtime2_Hour = (String) req.getParameter("alarm_Ringtime2_Hour");
		String alarm_Ringtime2_Minute = (String) req.getParameter("alarm_Ringtime2_Minute");
		String alarm_Ringtime3_Hour = (String) req.getParameter("alarm_Ringtime3_Hour");
		
		String alarm_Ringtime3_Minute = (String) req.getParameter("alarm_Ringtime3_Minute");
		String alarm_Volume = (String) req.getParameter("alarm_Volume");
		String alarm_Bell = (String) req.getParameter("alarm_Bell");
		String alarm_Vib = (String) req.getParameter("alarm_Vib");
		String alarm_Repeat = (String) req.getParameter("alarm_Repeat");
		
		model.addAttribute("alarm_Email", alarm_Email);
		model.addAttribute("alarm_Id", alarm_Id);
		model.addAttribute("alarm_Title", alarm_Title);
		model.addAttribute("alarm_Sunday", alarm_Sunday);
		model.addAttribute("alarm_Monday", alarm_Monday);
		model.addAttribute("alarm_Tuesday", alarm_Tuesday);
		
		model.addAttribute("alarm_Wednesday", alarm_Wednesday);
		model.addAttribute("alarm_Thursday", alarm_Thursday);
		model.addAttribute("alarm_Friday", alarm_Friday);
		model.addAttribute("alarm_Saturday", alarm_Saturday);
		model.addAttribute("alarm_Times", alarm_Times);
		
		model.addAttribute("alarm_Ringtime1_Hour", alarm_Ringtime1_Hour);
		model.addAttribute("alarm_Ringtime1_Minute", alarm_Ringtime1_Minute);
		model.addAttribute("alarm_Ringtime2_Hour", alarm_Ringtime2_Hour);
		model.addAttribute("alarm_Ringtime2_Minute", alarm_Ringtime2_Minute);
		model.addAttribute("alarm_Ringtime3_Hour", alarm_Ringtime3_Hour);
		
		model.addAttribute("alarm_Ringtime3_Minute", alarm_Ringtime3_Minute);
		model.addAttribute("alarm_Volume", alarm_Volume);
		model.addAttribute("alarm_Bell", alarm_Bell);
		model.addAttribute("alarm_Vib", alarm_Vib);
		model.addAttribute("alarm_Repeat", alarm_Repeat);
		
		System.out.println(alarm_Email + " / " + alarm_Id + " / " + alarm_Title + " / " + alarm_Sunday + " / " + alarm_Monday + " / " + alarm_Tuesday);
		System.out.println(alarm_Wednesday + " / " + alarm_Thursday + " / " + alarm_Friday + " / " + alarm_Saturday + " / " + alarm_Times);
		System.out.println(alarm_Ringtime1_Hour + " / " + alarm_Ringtime1_Minute + " / " + alarm_Ringtime2_Hour + " / " + alarm_Ringtime2_Minute + " / " + alarm_Ringtime3_Hour);
		System.out.println(alarm_Ringtime3_Minute + " / " + alarm_Volume + " / " + alarm_Bell + " / " + alarm_Vib + " / " + alarm_Repeat);
		
		command = new AlarmInsertCommand();
		command.execute(model);
		
		return "alarmInsert";
	}
	
	@RequestMapping(value="/alarmSelectMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String alarmSelectMulti(HttpServletRequest req, Model model){
		System.out.println("alarmSelectMulti()");
		
		command = new AlarmSelectMultiCommand();
		command.execute(model);
		
		return "alarmSelectMulti";
	}
	
	@RequestMapping(value = "/alarmUpdateMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public void alarmUpdateMulti(HttpServletRequest req, Model model) {
		System.out.println("alarmUpdateMulti()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String alarm_Email = (String) req.getParameter("alarm_Email");
		String alarm_Id = (String) req.getParameter("alarm_Id");
		String alarm_Title = (String) req.getParameter("alarm_Title");
		String alarm_Sunday = (String) req.getParameter("alarm_Sunday");
		String alarm_Monday = (String) req.getParameter("alarm_Monday");
		String alarm_Tuesday = (String) req.getParameter("alarm_Tuesday");
		
		String alarm_Wednesday = (String) req.getParameter("alarm_Wednesday");
		String alarm_Thursday = (String) req.getParameter("alarm_Thursday");
		String alarm_Friday = (String) req.getParameter("alarm_Friday");
		String alarm_Saturday = (String) req.getParameter("alarm_Saturday");
		String alarm_Times = (String) req.getParameter("alarm_Times");
		
		String alarm_Ringtime1_Hour = (String) req.getParameter("alarm_Ringtime1_Hour");
		String alarm_Ringtime1_Minute = (String) req.getParameter("alarm_Ringtime1_Minute");
		String alarm_Ringtime2_Hour = (String) req.getParameter("alarm_Ringtime2_Hour");
		String alarm_Ringtime2_Minute = (String) req.getParameter("alarm_Ringtime2_Minute");
		String alarm_Ringtime3_Hour = (String) req.getParameter("alarm_Ringtime3_Hour");
		
		String alarm_Ringtime3_Minute = (String) req.getParameter("alarm_Ringtime3_Minute");
		String alarm_Volume = (String) req.getParameter("alarm_Volume");
		String alarm_Bell = (String) req.getParameter("alarm_Bell");
		String alarm_Vib = (String) req.getParameter("alarm_Vib");
		String alarm_Repeat = (String) req.getParameter("alarm_Repeat");
		
		model.addAttribute("alarm_Email", alarm_Email);
		model.addAttribute("alarm_Id", alarm_Id);
		model.addAttribute("alarm_Title", alarm_Title);
		model.addAttribute("alarm_Sunday", alarm_Sunday);
		model.addAttribute("alarm_Monday", alarm_Monday);
		model.addAttribute("alarm_Tuesday", alarm_Tuesday);
		
		model.addAttribute("alarm_Wednesday", alarm_Wednesday);
		model.addAttribute("alarm_Thursday", alarm_Thursday);
		model.addAttribute("alarm_Friday", alarm_Friday);
		model.addAttribute("alarm_Saturday", alarm_Saturday);
		model.addAttribute("alarm_Times", alarm_Times);
		
		model.addAttribute("alarm_Ringtime1_Hour", alarm_Ringtime1_Hour);
		model.addAttribute("alarm_Ringtime1_Minute", alarm_Ringtime1_Minute);
		model.addAttribute("alarm_Ringtime2_Hour", alarm_Ringtime2_Hour);
		model.addAttribute("alarm_Ringtime2_Minute", alarm_Ringtime2_Minute);
		model.addAttribute("alarm_Ringtime3_Hour", alarm_Ringtime3_Hour);
		
		model.addAttribute("alarm_Ringtime3_Minute", alarm_Ringtime3_Minute);
		model.addAttribute("alarm_Volume", alarm_Volume);
		model.addAttribute("alarm_Bell", alarm_Bell);
		model.addAttribute("alarm_Vib", alarm_Vib);
		model.addAttribute("alarm_Repeat", alarm_Repeat);

		System.out.println(alarm_Email + " / " + alarm_Id + " / " + alarm_Title + " / " + alarm_Sunday + " / " + alarm_Monday + " / " + alarm_Tuesday);
		System.out.println(alarm_Wednesday + " / " + alarm_Thursday + " / " + alarm_Friday + " / " + alarm_Saturday + " / " + alarm_Times);
		System.out.println(alarm_Ringtime1_Hour + " / " + alarm_Ringtime1_Minute + " / " + alarm_Ringtime2_Hour + " / " + alarm_Ringtime2_Minute + " / " + alarm_Ringtime3_Hour);
		System.out.println(alarm_Ringtime3_Minute + " / " + alarm_Volume + " / " + alarm_Bell + " / " + alarm_Vib + " / " + alarm_Repeat);
		
		command = new AlarmUpdateMultiCommand();
		command.execute(model);
	}
	
	@RequestMapping(value = "/alarmDeleteMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public void alarmDeleteMulti(HttpServletRequest req, Model model) {
		System.out.println("alarmDeleteMulti()");

		String alarm_Id = (String) req.getParameter("alarm_Id");
		System.out.println("a : " + alarm_Id);
		model.addAttribute("alarm_Id", alarm_Id);
		System.out.println(alarm_Id);

		command = new AlarmDeleteMultiCommand();
		command.execute(model);
	}
	
	@RequestMapping(value = "/infoSelectMulti", method = { RequestMethod.GET, RequestMethod.POST })
	public String infoSelectMulti(HttpServletRequest req, Model model) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String info_email = req.getParameter("info_email"); 
		System.out.println("infoSelectMulti : " + info_email);
		model.addAttribute("info_email", info_email);
		
		command = new InfoSelectMultiCommand();
		command.execute(model);

		return "infoSelectMulti";
	}
}