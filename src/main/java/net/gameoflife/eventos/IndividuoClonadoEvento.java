package net.gameoflife.eventos;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class IndividuoClonadoEvento extends IndividuoEvento{
    private UUID padreUUID;

}
