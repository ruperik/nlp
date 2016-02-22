package com.patton.david.nlp;

import com.patton.david.nlp.serialization.Serializer;
import com.patton.david.nlp.structures.TextDocument;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

/*
 *  Command Line Interface for Natural Language Processor.
 *  It currently takes 2 options:
 *      -h,--help                  print help
 *      -i,--ignorecase            ignore case when parsing text
 *  You are also expected to pass a source file and target file
 */
public class NaturalLanguageProcessorCli {

    public static final String HELP_FORMAT = "nlp [OPTIONS] source_file target_file";
    public static final String HELP_SHORT_OPTION = "h";
    public static final String HELP_LONG_OPTION = "help";
    public static final String HELP_DESCRIPTION = "print help";
    public static final String IGNORE_CASE_SHORT_OPTION = "i";
    public static final String IGNORE_CASE_LONG_OPTION = "ignorecase";
    public static final String IGNORE_CASE_DESCRIPTION = "ignore case when parsing text";

    private Options options;

    public NaturalLanguageProcessorCli() {
        this(getDefaultCommandLineOptions());
    }

    public NaturalLanguageProcessorCli(Options options) {
        this.options = options;
    }

    public static void main(String[] args) throws IOException {
        NaturalLanguageProcessorCli nlpCli = new NaturalLanguageProcessorCli();
        nlpCli.run(args);
    }

    public void run(String[] args) throws IOException {
        //Get command line
        CommandLine cli = getCommandLine(args);
        //Check if help is requested; print help and return if true
        if (printHelp(cli)) {
            return;
        }
        //Initialize NLP
        NaturalLanguageProcessorOptions nlpOptions = parseNaturalLanguageProcessorOptions(cli);
        NaturalLanguageProcessor processor = new NaturalLanguageProcessor(nlpOptions);
        //Parse document from file
        List<String> filenames = cli.getArgList();
        if (filenames.size() < 2) {
            throw new IllegalArgumentException("Please pass source and target filenames");
        } else if (filenames.size() > 2){
            throw new IllegalArgumentException("Please do not pass more than source and target filenames");
        }
        String sourceFilename = filenames.get(0);
        String targetFilename = filenames.get(1);
        TextDocument document = processor.parseFile(sourceFilename);
        //Serialize document to file
        Serializer serializer = new Serializer();
        serializer.serializeToFile(document, targetFilename);
    }

    private static Options getDefaultCommandLineOptions() {
        Options cliOptions = new Options();
        cliOptions.addOption(HELP_SHORT_OPTION, HELP_LONG_OPTION, false, HELP_DESCRIPTION);
        cliOptions.addOption(IGNORE_CASE_SHORT_OPTION, IGNORE_CASE_LONG_OPTION, false, IGNORE_CASE_DESCRIPTION);
        return cliOptions;
    }

    private CommandLine getCommandLine(String[] args) {
        CommandLine cli;
        try {
            CommandLineParser parser = new DefaultParser();
            cli = parser.parse(this.options, args);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
        return cli;
    }

    private NaturalLanguageProcessorOptions parseNaturalLanguageProcessorOptions(CommandLine cli) {
        NaturalLanguageProcessorOptions nlpOptions = new NaturalLanguageProcessorOptions();
        if (cli.hasOption(IGNORE_CASE_SHORT_OPTION)) {
            nlpOptions.setIgnoreCase(true);
        }
        return nlpOptions;
    }

    private boolean printHelp(CommandLine cli) {
        if (cli.hasOption(HELP_SHORT_OPTION)) {
            HelpFormatter help = new HelpFormatter();
            help.printHelp(HELP_FORMAT, this.options);
            return true;
        }
        return false;
    }
}
