package com.bccns.umsserviceweb.common.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bccns.umsserviceweb.common.service.CommonService;
import com.bccns.umsserviceweb.common.util.AESCipherHdUtil;
import com.bccns.umsserviceweb.common.util.CryptUtils;
import com.bccns.umsserviceweb.common.util.DateUtils;
import com.bccns.umsserviceweb.common.util.StringUtil;
import com.bccns.umsserviceweb.common.util.UmsServiceWebSession;
import com.bccns.umsserviceweb.common.vo.CallBackVO;
import com.bccns.umsserviceweb.common.vo.CommCodeVO;
import com.bccns.umsserviceweb.common.vo.JsonVO;
import com.bccns.umsserviceweb.common.vo.MobileVO;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;
import com.bccns.umsserviceweb.push.conf.PushConst;
import com.google.gson.Gson;

@Controller
public class LoginController extends DefaultAPIController {
	private static final Logger log = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	CommonService commonService;

	@Value("${SSO.serviceID}")
	String serviceID;
	@Value("${SSO.domain}")
	String domain;
	@Value("${SSO.registURL}")
	String registURL;

	/**
	 * 
	 * ?????????
	 * 
	 * @param returnURL
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String login(@RequestParam("returnURL") String returnURL,
			@ModelAttribute("umsUserVO") UmsUserVO umsUserVO, Model model,
			HttpServletRequest request) {

		if (log.isDebugEnabled()) {
			log.debug("returnURL : " + returnURL);
		}

		model.addAttribute("serviceID", serviceID);
		model.addAttribute("domain", domain);
		model.addAttribute("registURL", registURL);
		model.addAttribute("returnURL", returnURL);

		return "/common/login";
	}

	/**
	 * ???????????????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/loginRegist", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String loginRegist(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttrs, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {

		String SSOTokenID = request.getParameter("SSOTokenID");
		@SuppressWarnings("deprecation")
		String returnURL = java.net.URLDecoder.decode(request
				.getParameter("returnURL"));
		String paramGUID = request.getParameter("GUID");
		String GUID = null; // request.getParameter("GUID");
		String optVal = request.getParameter("optVal");
		String encTk = request.getParameter("encTk");

		boolean isValidSSOSession = false;
		boolean isSuccess = true;
		log.debug("LOGIN:start");
		if (log.isDebugEnabled()) {
			log.debug("SSOTokenID[" + SSOTokenID + "] returnURL[" + returnURL
					+ "] optVal[" + optVal + "] encTk[" + encTk + "]");
		}

		// ???????????? ?????????
		umsUserVO.setUserPw(CryptUtils.AES_Encode(umsUserVO.getUserPw()));

		if (StringUtils.isEmpty(umsUserVO.getUserId())
				|| StringUtils.isEmpty(umsUserVO.getUserPw())) {
			// ScriptUtils.jUnblockMsgFromParent(out(), "??????", "????????? ?????????????????????");
			model.addAttribute("msg", "???????????? ??????????????? ???????????? ????????????.");
			return "/common/main";
		}

		log.debug("decode pw : " + CryptUtils.AES_Decode(umsUserVO.getUserPw()));
		// umsUserVO.setUserPw(CryptUtils.encryptSHA256Hash(umsUserVO.getUserPw()));
		log.debug("LOGIN:getUserInfo" + umsUserVO);
		UmsUserVO userVO = null;
		try {
			userVO = commonService.getUserInfo(umsUserVO);

			if (userVO == null) {
				log.debug("LOGIN: userId or userPw is wrong!");
				model.addAttribute("msg", "???????????? ??????????????? ???????????? ????????????.");
				return "/common/main";
			}
			log.debug("LOGIN:userVO" + userVO.getUserNm());
			log.debug("LOGIN:userVO.company" + userVO.getCompany());
			/*
			 * if (userVO.getLoginYn().equals("N")) {
			 * log.debug("LOGIN: login ago 90days!"); model.addAttribute("msg",
			 * "90????????? ???????????? ???????????? ????????? ???????????????. ?????????????????? ???????????? ???????????????."); return
			 * "/common/pwModify"; }
			 * 
			 * 
			 * if (userVO.getUpdateDay()!= null &&
			 * Integer.parseInt(userVO.getUpdateDay()) > 180) {
			 * log.debug("LOGIN: password expire!"); model.addAttribute("msg",
			 * "??????????????? ?????????????????????. ?????????????????? ???????????? ???????????????."); return "/common/pwModify";
			 * }
			 */

			if (isSuccess) {
				UmsServiceWebSession.make(request, userVO);

				// if (userVO.getUpdateDay()!= null &&
				// Integer.parseInt(userVO.getUpdateDay()) > 173) {
				// log.debug("LOGIN: {} pwModify !" );
				// model.addAttribute("msg", "????????????????????????. <br>???????????? ???????????? "
				// + (180 - Integer.parseInt(userVO
				// .getUpdateDay()))
				// + "??? ???????????????. <br>??????????????? ??????????????????.");
				// return "/common/main";
				// } else

				if (returnURL.equals("/common/login")) {
					log.debug("LOGIN: {} Success!");
					model.addAttribute("msg", "????????????????????????.");
					return "/common/main";
				} else {
					log.debug("LOGIN: {} Success!");
					model.addAttribute("msg", "????????????????????????.");
					return "redirect:/" + returnURL;
				}
			} else {
				log.debug("LOGIN: {} fail!");
				model.addAttribute("msg", "?????????  ??????. ??????????????? ???????????????.");
				return "/common/main";
			}
		} catch (Exception e) {
			log.debug("getUserInfo failure = " + e.getMessage());
		}

		return "/common/main";
	}

	@RequestMapping(value = "/api/mobile", method = {RequestMethod.POST })
	@ResponseBody
	public MobileVO mobileApi(@ModelAttribute MobileVO mobileVO) throws Exception {
					
		//?????? VO
		MobileVO respVo = new MobileVO();
		
		String userId 		= mobileVO.getUserId();
		String userPw 		= mobileVO.getUserPw();
		String deviceToken  = mobileVO.getDeviceToken();
			
		try {			
			// ??????  ?????? ??????
			if (mobileVO == null ||														
					userId == null || userId.isEmpty() || 
					userPw == null || userPw.isEmpty()) {
				
				mobileVO.setUserId(AESCipherHdUtil.getInstance().AES_Decode(userId));
				mobileVO.setResult_cd("E00001");
				mobileVO.setResult_msg("?????????????????? ???????????????.");
				throw new Exception("?????????????????? ???????????????.");
			}
			
			//????????? Decode
			mobileVO.setUserId(AESCipherHdUtil.getInstance().AES_Decode(userId));
			mobileVO.setUserPw(AESCipherHdUtil.getInstance().AES_Decode(userPw));
			mobileVO.setDeviceToken(AESCipherHdUtil.getInstance().AES_Decode(deviceToken));

			// ?????? ??? ??????????????? ????????? ??????
			mobileVO.setUserPw(CryptUtils.AES_Encode(mobileVO.getUserPw()));						
	
			respVo = commonService.getMobileApi(mobileVO);							// 1. ????????? ?????? ??????(USER_ID, USER_PW, USER_NM, PHONE_NO)
	
			if (respVo == null) {													// 2. ????????? ?????? ?????? ?????? ??????
				mobileVO.setResult_cd("E00100");
				mobileVO.setResult_msg("???????????????");
				throw new Exception("???????????????");
			}
			
			if (respVo.getOsGb() == null) {                                     // 3. Push DB ????????? ??????????????? ????????? ?????? ??????
				mobileVO.setResult_cd("E00002");
				mobileVO.setResult_msg("???????????? ???????????? ????????????.");
				throw new Exception("???????????? ???????????? ????????????.");
			}
			
			
			if ("I".equals(respVo.getOsGb())
					|| "A".equals(respVo.getOsGb())) {							// 4. OS_GB ??? ??? I(?????????) ?????????  A(???????????????) ??? ??????
				
				commonService.modifyMobileApi(mobileVO);			            // 5. DeviceToken ??????
		        commonService.modifyMobileApi2(mobileVO);                       // 6. OS_GB ??????
			}
			
			respVo.setOsGb(mobileVO.getOsGb());
			respVo.setDeviceToken(mobileVO.getDeviceToken());
			respVo.setResult_cd("S00000");
			respVo.setResult_msg("???????????????");
			respVo.setUserPw(null);
			return respVo;

			
		} catch (Exception e) {
			log.error("login error", e);
			
			mobileVO.setUserPw(null);
			
			if (mobileVO.getResult_cd() == null) {
				mobileVO.setResult_cd("E00100");
				mobileVO.setResult_msg("???????????????");
			}
			
			return mobileVO;
		}
	}
	
	@RequestMapping(value = "/api/mobileUpdate", method = {RequestMethod.POST })
	@ResponseBody
	public MobileVO mobileUpdateApi(@ModelAttribute MobileVO mobileVO) throws Exception {
					
		
		String deviceToken = mobileVO.getDeviceToken();
		String phone = mobileVO.getTelNo2();
		
		try {
			
			//????????? Decode
			mobileVO.setDeviceToken(AESCipherHdUtil.getInstance().AES_Decode(deviceToken));
			mobileVO.setTelNo2(AESCipherHdUtil.getInstance().AES_Decode(phone));
			
			if (mobileVO == null ||														
				deviceToken == null || deviceToken.isEmpty() || 
				phone == null || phone.isEmpty()) {
				
			 mobileVO.setResult_cd("E00100");
			 mobileVO.setResult_msg("???????????? ??????");
			 throw new Exception("???????????? ??????");
			  
			}
			
		
			 commonService.modifyMobileUpdateApi(mobileVO);			// 1. deviceToken ????????????
			 mobileVO.setResult_cd("S00000");
			 mobileVO.setResult_msg("???????????? ??????");
		      
			 return mobileVO;
			 
		    } catch (Exception e) {
			
			log.error("login error", e);
			mobileVO.setResult_cd("E00100");
			mobileVO.setResult_msg("???????????? ??????");
			
			return mobileVO;

		}
	}

	/**
	 * 
	 * ????????????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String logout(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		boolean isValidSSOSession = false;

		/* Local ???????????? ????????? Session??? ????????? ????????????. */
		UmsServiceWebSession.reset(request);
		return "/common/logout";
	}

	/**
	 * ????????????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getRegUsr")
	public String getRegUsr(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttrs, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {

		List<CommCodeVO> deptCdList = new ArrayList<CommCodeVO>();
		CommCodeVO commCodeVO = new CommCodeVO();
		commCodeVO.setCodeGroup("DEPT_CD");
		try {
			deptCdList = commonService.getCodeList(commCodeVO);
		} catch (Exception e) {
			log.debug("getCodeList failure = " + e.getMessage());
		}
		log.debug("deptCdList = " + deptCdList.toString());

		List<CommCodeVO> posCdList = new ArrayList<CommCodeVO>();
		commCodeVO.setCodeGroup("POS_CD");
		try {
			posCdList = commonService.getCodeList(commCodeVO);
		} catch (Exception e) {
			log.debug("getCodeList failure = " + e.getMessage());
		}
		log.debug("posCdList = " + posCdList.toString());

		List<CommCodeVO> comCdList = new ArrayList<CommCodeVO>();
		commCodeVO.setCodeGroup("COM_CD");
		try {
			comCdList = commonService.getCodeList(commCodeVO);
		} catch (Exception e) {
			log.debug("getCodeList failure = " + e.getMessage());
		}
		log.debug("comCdList = " + comCdList.toString());

		model.addAttribute("deptCdList", deptCdList);
		model.addAttribute("posCdList", posCdList);
		model.addAttribute("comCdList", comCdList);

		return "/common/regUser";
	}

	/**
	 * ????????????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/regUsrProcAjax")
	public @ResponseBody
	JsonVO registUser(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			SessionStatus status, RedirectAttributes redirectAttrs,
			Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		JsonVO result = new JsonVO();
		String msg = "success!";
		String mav = "/common/regUser";
		Integer res = 0;
		try {

			/*
			 * 10:GUEST 11:GUEST(??????) 20:??????(?????????) 30:????????? (????????????:USER_LV)
			 */
			umsUserVO.setUserLv("10");
			umsUserVO.setConfYn("N");
			umsUserVO.setInstId(umsUserVO.getUserId());
			umsUserVO.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));

			if (!umsUserVO.getUserPw().equals(request.getParameter("userPwC"))) {
				result.setResult(false);
				result.setErrMsg("???????????? ?????? ?????????????????????.");
				log.debug("registUser password fail");
			} else {
				// ???????????? ??????
				if (umsUserVO.getCompany().equals("DIRECT")) {
					CommCodeVO commCodeVO = new CommCodeVO();
					commCodeVO.setCodeGroup("COM_CD");
					commCodeVO = commonService.getCodeValAddCom(commCodeVO);
					commCodeVO.setCode(StringUtil.fillZero(
							Integer.valueOf(commCodeVO.getCode()), 2));
					umsUserVO.setCompany(commCodeVO.getCode());
					commCodeVO.setCodeName(umsUserVO.getCompanyNm());
					commCodeVO.setInstId(umsUserVO.getUserId());
					commCodeVO
							.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
					commonService.createCode(commCodeVO);

				}
				// ???????????? ??????
				if (umsUserVO.getDept().equals("DIRECT")) {
					CommCodeVO commCodeVO = new CommCodeVO();
					commCodeVO.setCodeGroup("DEPT_CD");
					commCodeVO = commonService.getCodeValAddDept(commCodeVO);
					commCodeVO.setCode(StringUtil.fillZero(
							Integer.valueOf(commCodeVO.getCode()), 2));
					umsUserVO.setDept(commCodeVO.getCode());
					commCodeVO.setCodeName(umsUserVO.getDeptNm());
					commCodeVO.setInstId(umsUserVO.getUserId());
					commCodeVO
							.setInstDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
					commonService.createCode(commCodeVO);
				}
				log.debug("registUser password bef=" + umsUserVO.getUserPw());

				// ?????????
				umsUserVO
						.setUserPw(CryptUtils.AES_Encode(umsUserVO.getUserPw()));

				log.debug("registUser password aft=" + umsUserVO.getUserPw());

				res = commonService.createUser(umsUserVO);
				if (res == 0) {
					result.setResult(false);
					log.debug("registUser fail");
					result.setErrMsg("???????????? ?????????????????????.");

				} else if (res == 1) {
					result.setResult(true);
					log.debug("registUser success");
					result.setSucMsg("???????????? ?????????????????????.");
				}
			}
		} catch (Exception e) {
			log.debug("registUser fail");
			result.setResult(false);
			result.setErrMsg(e.getMessage());
			log.error("registUser error", e);
		}
		model.addAttribute(umsUserVO);
		// Map<String, Object> forwardMap = UtilStatic.getMap(umsUserVO);
		// redirectAttrs.addAllAttributes(forwardMap);
		return result;
	}

	/**
	 * ?????????????????? ??????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/getModUsrPw", "/mgr/getModUsrPw" })
	public String getNodifyUserPw(
			@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttrs, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {

		try {

			String userId = UmsServiceWebSession.getSessionUserId(request);

			umsUserVO.setUserId(userId);
			umsUserVO = commonService.getUserInfoD(umsUserVO);
			model.addAttribute(umsUserVO);

			log.debug("umsUserVO = " + umsUserVO.toString());
		} catch (Exception e) {
			log.error("getModUsr error", e);
		}
		return "/common/modUserPw";
	}

	/**
	 * ????????????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/getModUsr", "/mgr/getModUsr" })
	public String getNodifyUser(
			@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttrs, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {

		try {
			List<CommCodeVO> deptCdList = new ArrayList<CommCodeVO>();
			CommCodeVO commCodeVO = new CommCodeVO();
			commCodeVO.setCodeGroup("DEPT_CD");
			try {
				deptCdList = commonService.getCodeList(commCodeVO);
			} catch (Exception e) {
				log.debug("getCodeList failure = " + e.getMessage());
			}
			log.debug("deptCdList = " + deptCdList.toString());

			List<CommCodeVO> posCdList = new ArrayList<CommCodeVO>();
			commCodeVO.setCodeGroup("POS_CD");
			try {
				posCdList = commonService.getCodeList(commCodeVO);
			} catch (Exception e) {
				log.debug("getCodeList failure = " + e.getMessage());
			}
			log.debug("posCdList = " + posCdList.toString());

			List<CommCodeVO> comCdList = new ArrayList<CommCodeVO>();
			commCodeVO.setCodeGroup("COM_CD");
			try {
				comCdList = commonService.getCodeList(commCodeVO);
			} catch (Exception e) {
				log.debug("getCodeList failure = " + e.getMessage());
			}
			log.debug("comCdList = " + comCdList.toString());

			List<CallBackVO> callbackList = new ArrayList<CallBackVO>();
			try {
				CallBackVO callBackVO = new CallBackVO();

				String userId = (String) session.getAttribute("sessionUId");
				callBackVO.setUserId(userId);

				callbackList = commonService.getCallBackInfo(callBackVO);
			} catch (Exception e) {
				log.debug("getCallBackInfo failure = " + e.getMessage());
			}
			log.debug("callbackList = " + comCdList.toString());

			// model.addAttribute("deptCdList",deptCdList);
			// model.addAttribute("posCdList",posCdList);
			// model.addAttribute("comCdList",comCdList);

			String userId = UmsServiceWebSession.getSessionUserId(request);

			umsUserVO.setUserId(userId);
			umsUserVO = commonService.getUserInfoD(umsUserVO);
			umsUserVO.setDeptCdList(deptCdList);
			umsUserVO.setPosCdList(posCdList);
			umsUserVO.setComCdList(comCdList);
			umsUserVO.setCallbackList(callbackList);
			model.addAttribute(umsUserVO);

			log.debug("umsUserVO = " + umsUserVO.toString());
		} catch (Exception e) {
			log.error("getModUsr error", e);
		}
		return "/common/modUser";
	}

	/**
	 * ??????????????????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/modUsrProc")
	public String modifyUser(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttrs, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {

		String msg = "success!";
		String mav = "/common/modUser";
		Integer res = 0;
		try {

			List<CommCodeVO> deptCdList = new ArrayList<CommCodeVO>();
			CommCodeVO commCodeVO = new CommCodeVO();
			commCodeVO.setCodeGroup("DEPT_CD");
			try {
				deptCdList = commonService.getCodeList(commCodeVO);
			} catch (Exception e) {
				log.debug("getCodeList failure = " + e.getMessage());
			}
			log.debug("deptCdList = " + deptCdList.toString());

			List<CommCodeVO> posCdList = new ArrayList<CommCodeVO>();
			commCodeVO.setCodeGroup("POS_CD");
			try {
				posCdList = commonService.getCodeList(commCodeVO);
			} catch (Exception e) {
				log.debug("getCodeList failure = " + e.getMessage());
			}
			log.debug("posCdList = " + posCdList.toString());

			List<CommCodeVO> comCdList = new ArrayList<CommCodeVO>();
			commCodeVO.setCodeGroup("COM_CD");
			try {
				comCdList = commonService.getCodeList(commCodeVO);
			} catch (Exception e) {
				log.debug("getCodeList failure = " + e.getMessage());
			}
			log.debug("comCdList = " + comCdList.toString());

			String userId = UmsServiceWebSession.getSessionUserId(request);
			UmsUserVO umsUserVOCmp = new UmsUserVO();
			umsUserVOCmp.setUserId(userId);
			umsUserVOCmp = commonService.getUserInfoD(umsUserVOCmp);

			log.debug(umsUserVO.toString());
			umsUserVO.setUpdtId(umsUserVO.getUserId());
			umsUserVO.setUpdtDt(DateUtils.getSysDate("yyyyMMddHHmmss"));

			// ???????????? ?????????
			umsUserVO.setUserPw(CryptUtils.AES_Encode(umsUserVO.getUserPw()));

			res = commonService.modifyUser(umsUserVO);

			// ??????,??????????????? ??????????????????
			if ((!umsUserVOCmp.getDept().equals(umsUserVO.getDept()) || !umsUserVOCmp
					.getCompany().equals(umsUserVO.getCompany()))
					&& (Integer.valueOf(umsUserVOCmp.getUserLv()) < 40)
					&& res == 0) {
				umsUserVOCmp.setUserId(userId);
				umsUserVOCmp.setConfYn("N");
				umsUserVOCmp.setUserLv("10");
				umsUserVOCmp.setUpdtDt(DateUtils.getSysDate("yyyyMMddHHmmss"));
				umsUserVOCmp.setUpdtId(userId);

				res = commonService.modifyUmsUserAppl(umsUserVOCmp);
				log.debug("umsUserVOCmp = " + umsUserVOCmp.toString());
			}
			log.debug("res ------------->> " + res);
			if (res == 0) {
				log.debug("registUser fail");
				model.addAttribute("msg", "???????????? ??????");

			} else if (res == 1) {
				model.addAttribute("msg", "???????????? ??????");
				model.addAttribute("umsUserVO", umsUserVO);
				log.debug("registUser success");
			}

			umsUserVO.setUserId(userId);
			umsUserVO = commonService.getUserInfoD(umsUserVO);

			umsUserVO.setDeptCdList(deptCdList);
			umsUserVO.setPosCdList(posCdList);
			umsUserVO.setComCdList(comCdList);
			model.addAttribute(umsUserVO);
		} catch (Exception e) {
			log.debug("modify fail");
			model.addAttribute("msg", "????????? ???????????????.");
		}

		return mav;

	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/modUsrPwProc")
	public String modifyUserPw(
			@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttrs, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {

		String msg = "success!";
		String mav = "/common/modUserPw";
		Integer res = 0;
		try {

			String userId = UmsServiceWebSession.getSessionUserId(request);
			UmsUserVO umsUserVOCmp = new UmsUserVO();
			umsUserVOCmp.setUserId(userId);
			umsUserVOCmp.setUserPw(CryptUtils.AES_Encode(request
					.getParameter("userPwB")));
			umsUserVOCmp = commonService.getUserInfo(umsUserVOCmp);
			if (umsUserVOCmp == null) {
				log.debug("LOGIN: userId or userPw is wrong!");
				model.addAttribute("msg", "??????????????? ???????????? ????????????.");
				return mav;
			}
			if (!umsUserVO.getUserPw().equals(request.getParameter("userPwC"))) {
				log.debug("registUser password fail");
				model.addAttribute("msg", "???????????? ?????? ?????????????????????.");
			}

			log.debug(umsUserVO.toString());
			umsUserVO.setUpdtId(umsUserVO.getUserId());
			umsUserVO.setUpdtDt(DateUtils.getSysDate("yyyyMMddHHmmss"));

			// ???????????? ?????????
			umsUserVO.setUserPw(CryptUtils.AES_Encode(umsUserVO.getUserPw()));

			res = commonService.modifyPassword(umsUserVO);

			log.debug("res ------------->> " + res);
			if (res == 0) {
				log.debug("registUser fail");
				model.addAttribute("msg", "???????????? ??????");

			} else if (res == 1) {
				model.addAttribute("msg", "???????????? ??????");
				model.addAttribute("umsUserVO", umsUserVO);
				log.debug("registUser success");
			}

			umsUserVO.setUserId(userId);
			umsUserVO = commonService.getUserInfoD(umsUserVO);

			model.addAttribute(umsUserVO);
		} catch (Exception e) {
			log.debug("modify fail");
			model.addAttribute("msg", "????????? ???????????????.");
		}

		return mav;

	}

	/**
	 * ID/PW ??????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/seeKIdPw")
	public String getSeekIdPw(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttrs, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		return "/common/seekIdPw";
	}

	/**
	 * ID ??????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/seeKIdProcAjax")
	public @ResponseBody
	Map getSeeKIdProcAjax(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String msg = "";
		try {
			umsUserVO.setUserNm(request.getParameter("userNm1"));
			umsUserVO.setEmail(request.getParameter("email1"));
			log.debug(umsUserVO.toString());
			umsUserVO = commonService.getSeekId(umsUserVO);
			if (StringUtils.isNotEmpty(umsUserVO.getUserId())) {
				msg = umsUserVO.getUserId();
				result.put("result", true);
				result.put("msg", msg);

			} else {
				msg = "invalid Id";
				result.put("result", false);
				result.put("msg", msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("result", false);
			result.put("msgDtl", e.getMessage());
			result.put("msg", "System error :: ums content info create fail.");
		}
		return result;

	}

	/**
	 * Password ??????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/seeKPwProcAjax")
	public @ResponseBody
	Map getSeeKPwProcAjax(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String msg = "";
		try {
			umsUserVO.setUserId(request.getParameter("userId2").trim());
			umsUserVO.setUserNm(request.getParameter("userNm2").trim());
			umsUserVO.setEmail(request.getParameter("email2").trim());
			umsUserVO.setQue(request.getParameter("que").trim());
			umsUserVO.setAns(request.getParameter("ans").trim());

			umsUserVO = commonService.getSeekPw(umsUserVO);
			if (StringUtils.isNotEmpty(umsUserVO.getUserPw())) {
				// msg = umsUserVO.getUserPw();
				msg = CryptUtils.AES_Decode(umsUserVO.getUserPw());
				result.put("result", true);
				result.put("msg", msg);
				log.debug(umsUserVO.toString());

			} else {
				msg = "invalid Id";
				result.put("result", false);
				result.put("msg", msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("result", false);
			result.put("msgDtl", e.getMessage());
			result.put("msg", "System error :: ums content info create fail.");
		}
		return result;

	}

	/**
	 * Id ??????
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/valIdProcAjax")
	public @ResponseBody
	Map getValIdProcAjax(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		String msg = "";
		try {
			Integer idCnt = commonService.getIdValid(umsUserVO);
			if (idCnt == 0) {
				msg = "available Id";
				result.put("result", true);
				result.put("msg", msg);

			} else {
				msg = "invalid Id";
				result.put("result", false);
				result.put("msg", msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("result", false);
			result.put("msgDtl", e.getMessage());
			result.put("msg", "System error :: ums content info create fail.");
		}
		return result;
	}

	/**
	 * ????????????
	 * 
	 * @param umsUserVO
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/validSession")
	public @ResponseBody
	JsonVO getValId(@ModelAttribute("umsUserVO") UmsUserVO umsUserVO,
			ModelMap model, HttpSession session) {

		JsonVO result = new JsonVO();
		String msg = "";
		try {
			if (null != session.getAttribute("SESSION_USER")) {
				result.setResult(true);
				result.setErrMsg(msg);
			} else {
				result.setResult(false);
				msg = "invalid";
				result.setErrMsg(msg);
			}
		} catch (Exception e) {
			log.error("invalidSessionResultAjax error :: ", e);
			result.setResult(false);
			result.setErrMsg("invalidSession fail.");
		}
		log.debug("result => " + result.isResult());

		return result;
	}

	@RequestMapping(value = "/getTestSession")
	public String testSession(Locale locale, Model model, HttpSession session) {

		if (null == session.getAttribute("SESSION_USER")) {
			session.setAttribute("SESSION_USER", "TestSession");
		}
		log.debug("SESSION_USER [" + session.getAttribute("SESSION_USER") + "]");

		return "/common/logout";
	}

	/**
	 * Callback ??????/?????? ??????
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/callbackPopup")
	public String getCallbackPopup(HttpServletRequest request, Model model) {
		String type = request.getParameter("type");

		if ("D".equals(type)) {
			String userId = UmsServiceWebSession.getSessionUserId(request);
			CallBackVO callBackVO = new CallBackVO();
			callBackVO.setUserId(userId);
			List<CallBackVO> callbackList = commonService
					.getCallBackInfo(callBackVO);
			model.addAttribute("callbackList", callbackList);
		}

		return "/common/callbackPop";
	}

	/**
	 * Callback ?????? ??????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkCallbackAjax")
	public @ResponseBody
	boolean getCheckCallbackAjax(HttpServletRequest request) {

		CallBackVO callBackVO = new CallBackVO();

		String userId = UmsServiceWebSession.getSessionUserId(request);
		callBackVO.setUserId(userId);

		String callbackNo = request.getParameter("callbackNo");
		callBackVO.setCallbackNo(callbackNo.replace("-", ""));

		try {
			List<CallBackVO> list = commonService.getCallBackInfo(callBackVO);
			if (list == null || list.isEmpty())
				return true;
			else
				return false;
		} catch (Exception e) {
			log.debug("checkCallback failure = " + e.getMessage());
			return false;
		}
	}

	/**
	 * ???????????? ??????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/authProcAjax")
	public @ResponseBody
	boolean getAuthProcAjax(HttpServletRequest request) {

		CallBackVO callBackVO = new CallBackVO();

		String userId = UmsServiceWebSession.getSessionUserId(request);
		callBackVO.setUserId(userId);

		String callbackNo = request.getParameter("callbackNo");
		callBackVO.setCallbackNo(callbackNo.replace("-", ""));

		Random random = new Random();
		StringBuffer authCode = new StringBuffer();
		while (authCode.length() < 5) {
			int d = random.nextInt(9);
			authCode.append(Integer.toString(d));
		}
		callBackVO.setAuthCode(authCode.toString());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		callBackVO.setInstDt(dateFormat.format(new Date()));

		try {
			int rs = commonService.createAuthCode(callBackVO);
			if (rs == 0) {
				return false;
			} else {
				commonService.createAuthSms(callBackVO);
				return true;
			}
		} catch (Exception e) {
			log.debug("authProc failure = " + e.getMessage());
			return false;
		}
	}

	/**
	 * ???????????? ??????/??????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/regCallbackAjax")
	public @ResponseBody
	boolean getRegCallbackAjax(HttpServletRequest request) {

		CallBackVO callBackVO = new CallBackVO();

		String userId = UmsServiceWebSession.getSessionUserId(request);
		callBackVO.setUserId(userId);

		String callbackNo = request.getParameter("callbackNo");
		callBackVO.setCallbackNo(callbackNo.replace("-", ""));

		String authCode = request.getParameter("authCode");
		callBackVO.setAuthCode(authCode);

		try {
			int rs = commonService.createCallbackInfo(callBackVO);
			if (rs == 0) {
				return false;
			} else {
				commonService.createCallbackInfoHis(callBackVO);
				commonService.delAuthCode(callBackVO);
				return true;
			}
		} catch (Exception e) {
			log.debug("regCallback failure = " + e.getMessage());
			return false;
		}
	}

	/**
	 * Callback ?????? ??????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delCallbackAjax")
	public @ResponseBody
	boolean getDelCallbackAjax(HttpServletRequest request) {

		CallBackVO callBackVO = new CallBackVO();

		String userId = UmsServiceWebSession.getSessionUserId(request);
		callBackVO.setUserId(userId);

		String callbackNo = request.getParameter("callbackNo");
		callBackVO.setCallbackNo(callbackNo.replace("-", ""));

		String authCode = request.getParameter("authCode");
		callBackVO.setAuthCode(authCode);

		callBackVO.setStatus("D");

		try {
			commonService.createCallbackInfo(callBackVO);
			commonService.createCallbackInfoHis(callBackVO);
			int rs = commonService.delCallbackInfo(callBackVO);
			if (rs == 0) {
				return false;
			} else {
				commonService.delAuthCode(callBackVO);
				return true;
			}
		} catch (Exception e) {
			log.debug("getDelCallback failure = " + e.getMessage());
			return false;
		}
	}

	/**
	 * ???????????? ??????(?????????)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/callbackListAjax")
	public @ResponseBody
	List getCallbackListAjax(HttpServletRequest request) {

		CallBackVO callBackVO = new CallBackVO();

		String userId = UmsServiceWebSession.getSessionUserId(request);
		callBackVO.setUserId(userId);

		try {
			List<CallBackVO> callbackList = commonService
					.getCallBackInfo(callBackVO);
			return callbackList == null ? new ArrayList() : callbackList;
		} catch (Exception e) {
			log.debug("callbackList failure = " + e.getMessage());
			return new ArrayList();
		}
	}

	public static void main(String[] args) throws InvalidKeyException,
			UnsupportedEncodingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		System.out.println(CryptUtils.AES_Decode("iIGdxpO04/5zp3lt9Z/H9i01Ge3tC35kEQhK+dVRpGg="));
		System.out.println(CryptUtils.AES_Decode("iIGdxpO04/5zp3lt9Z/H9i01Ge3tC35kEQhK+dVRpGg="));
		System.out.println(CryptUtils.AES_Decode("iIGdxpO04/5zp3lt9Z/H9i01Ge3tC35kEQhK+dVRpGg="));
		System.out.println(CryptUtils.AES_Decode("iIGdxpO04/5zp3lt9Z/H9i01Ge3tC35kEQhK+dVRpGg="));
		System.out.println(CryptUtils.AES_Decode("iIGdxpO04/5zp3lt9Z/H9i01Ge3tC35kEQhK+dVRpGg="));
		System.out.println(CryptUtils.AES_Decode("iIGdxpO04/5zp3lt9Z/H9i01Ge3tC35kEQhK+dVRpGg="));
		System.out.println(CryptUtils.AES_Decode("iIGdxpO04/5zp3lt9Z/H9i01Ge3tC35kEQhK+dVRpGg="));
	}

}
