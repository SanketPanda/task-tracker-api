package com.task.tracker.service.notification;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	/*
	The email notification feature is disabled in timeapp

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	private static String ticketReportSubject = "Ticket report";

	private static String ticketAssignedToYouSubject = "A Ticket has been assigned to you in TimeApp : ";

	public String processTicketAssignedToYouTemplate(TicketDTO ticketDTO) {
		Context context = new Context();
		context.setVariable("ticket", ticketDTO);
		return templateEngine.process("ticket-assigned-to-you-template", context);
	}

	public String processTicketReportTemplate(TicketDTO ticketDTO, List<ActivityDTO> activityList, List<ArticleDTO> articleList) {
		Context context = new Context();
		context.setVariable("subject", ticketReportSubject);
		context.setVariable("ticket", ticketDTO);
		context.setVariable("activityList", activityList);
		context.setVariable("articleList", articleList);
		return templateEngine.process("ticket-report-template", context);
	}

	public void sendTicketReport(TicketDTO ticketDTO, List<ActivityDTO> activityList, List<ArticleDTO> articleList){
		String processedTemplate = this.processTicketReportTemplate(ticketDTO, activityList, articleList);
		StringBuilder stringBuilder = new StringBuilder(ticketReportSubject).append(": ").append(ticketDTO.getTitle());
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setTo(ticketDTO.getProjectDTO().getCustomerDTO().getContactEmail());
			messageHelper.setSubject(stringBuilder.toString());
			messageHelper.setText(processedTemplate, true); // Set true to indicate HTML content
			javaMailSender.send(mimeMessage);
		} catch (Exception exception){
			System.out.println("Exception occurred while sending the email notification");
			exception.printStackTrace();
		}
	}

	public void notifyTechnician(TicketDTO ticketDTO){
		String processedTemplate = this.processTicketAssignedToYouTemplate(ticketDTO);
		StringBuilder stringBuilder = new StringBuilder(ticketAssignedToYouSubject).append(ticketDTO.getTitle());
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setTo("sanketpanda37@gmail.com");
			messageHelper.setSubject(stringBuilder.toString());
			messageHelper.setText(processedTemplate, true); // Set true to indicate HTML content
			javaMailSender.send(mimeMessage);
		} catch (Exception exception){
			System.out.println("Exception occurred while sending the email notification");
			exception.printStackTrace();
		}
	}
	 */
}
