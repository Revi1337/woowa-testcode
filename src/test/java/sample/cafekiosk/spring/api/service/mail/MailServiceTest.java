package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

/**
 * 순수 Mockito 로만 테스트.
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    /**
     * (@Mock, @InjectMocks) 를 풀어쓰면 아래와같이 나타낼 수 있다.
     *
     * (@ExtendWith(MockitoExtension.class))
     * MailSendClient mailSendClient = mock(MailSendClient.class);
     * MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);
     * MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);
     */
    @Mock private MailSendClient mailSendClient;
    @Mock private MailSendHistoryRepository mailSendHistoryRepository;
    @InjectMocks private MailService mailService;

    @DisplayName("메일 전송 테스트 (Mockito)")
    @Test
    public void sendMail() {
        // given
        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

    @DisplayName("메일 전송 테스트 (BddMockito)")
    @Test
    public void sendMail2() {
        // given
        given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

//    @DisplayName("메일 전송 테스트")
//    @Test
//    public void sendMail() {
//        // given
//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(), anyString(), anyString(), anyString());
//
//        // when
//        boolean result = mailService.sendMail("", "", "", "");
//
//        // then
//        assertThat(result).isTrue();
//        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
//    }

}