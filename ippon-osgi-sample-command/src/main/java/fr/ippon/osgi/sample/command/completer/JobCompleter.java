package fr.ippon.osgi.sample.command.completer;

import java.util.List;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.karaf.shell.api.console.CommandLine;
import org.apache.karaf.shell.api.console.Completer;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.support.completers.StringsCompleter;

/**
 * Completion du job
 *
 * @author sfoubert
 */
@Service
public class JobCompleter implements Completer {

    @Override
    public int complete(Session session, CommandLine commandLine, List<String> candidates) {
        StringsCompleter delegate = new StringsCompleter();

        delegate.getStrings().add("DEV");
        delegate.getStrings().add("TECHLEAD");
        delegate.getStrings().add("ARCHITECT");
        delegate.getStrings().add("MANAGER");
        delegate.getStrings().add("HR");
        delegate.getStrings().add("CEO");

        return delegate.complete(session, commandLine, candidates);
    }

}