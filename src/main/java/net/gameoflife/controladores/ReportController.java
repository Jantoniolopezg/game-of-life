package net.gameoflife.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import lombok.RequiredArgsConstructor;
import net.gameoflife.eventos.Evento;
import net.gameoflife.servicios.EventoServicio;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportController extends BaseController{

    private final EventoServicio eventoServicio;

    @FXML
    private TextArea eventInfoArea;

    @Override
    public void init() {
        List<Evento> eventos = eventoServicio.getEventos();


        StringBuilder stringBuilder = new StringBuilder();
        eventos.forEach(evento -> {
            stringBuilder.append(evento.toString());
            stringBuilder.append("\n");
            stringBuilder.append("\n");
        });
        eventInfoArea.setText(stringBuilder.toString());

    }

}
