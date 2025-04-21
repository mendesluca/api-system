package com.eduardo.apisystem.service.email;

import com.eduardo.apisystem.entity.Usuario;
import exception.customizadas.usuario.UsuarioException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender enviadorEmail;

    private static final String EMAIL_ORIGEM = "eduardoschiliga1@gmail.com";
    private static final String NOME_ENVIADOR = "Api System";
    public static final String URL_SITE = "http://localhost:8150/api/system/usuario";

    @Async
    protected void enviarEmail(String emailUsuario, String assunto, String conteudo) {
        MimeMessage message = enviadorEmail.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(EMAIL_ORIGEM, NOME_ENVIADOR);
            helper.setTo(emailUsuario);
            helper.setSubject(assunto);
            helper.setText(conteudo, true);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new UsuarioException("Erro ao enviar email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        enviadorEmail.send(message);
    }

    public void enviarEmailVerificacao(Usuario usuario) {
        log.info("Host de envio: {}", ((JavaMailSenderImpl) enviadorEmail).getHost());

        String assunto = "Aqui está seu link para verificar o email";
        String conteudo = gerarConteudoEmail("Olá [[name]],<br>"
                + "Por favor clique no link abaixo para verificar sua conta:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFICAR</a></h3>"
                + "Obrigado,<br>"
                + "Site :).", usuario.getNomeCompleto(), URL_SITE + "/verificar-conta?codigo=" + usuario.getEmailToken());

        enviarEmail(usuario.getEmail(), assunto, conteudo);
    }

    private String gerarConteudoEmail(String template, String nome, String url) {
        return template.replace("[[name]]", nome).replace("[[URL]]", url);
    }
}
