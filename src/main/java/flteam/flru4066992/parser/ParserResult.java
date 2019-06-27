package flteam.flru4066992.parser;

import flteam.flru4066992.core.BetType;
import flteam.flru4066992.model.Match;

import java.util.List;

public class ParserResult {

    private final BetType type;
    private final List<Match> matches;

    public ParserResult(BetType type, List<Match> matches) {
        this.type = type;
        this.matches = matches;
    }

    public BetType getType() {
        return type;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
