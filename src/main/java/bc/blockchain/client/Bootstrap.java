package bc.blockchain.client;

import io.netty.channel.Channel;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Bootstrap extends BlockChainContext{
	
	public static void main(String[] args){
		
		Options options = new Options();
		Option opt = new Option("h", "help", false, "Print help");
		opt.setRequired(false);
		options.addOption(opt);

		opt = new Option("p", "printConfigItem", false, "Print all config item");
		opt.setRequired(false);
		options.addOption(opt);
		
		HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        CommandLine commandLine = null;
        CommandLineParser parser = new PosixParser();
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                // 打印使用帮助
                hf.printHelp("DYMLINK", options, true);
            }
            // 打印opts的名称和值
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt1 : opts) {
                    String name = opt1.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    System.out.println(name + "=>" + value);
                }
            }
        }
        catch (ParseException e) {
            hf.printHelp("testApp", options, true);
        }
        
        Bootstrap bs=new Bootstrap();
        bs.setSysEvn();
        Channel channel = bs.start();
        bs.regClient(channel);
        
//		Bootstrap bs=new Bootstrap();
//		bs.start();
//		System.out.println("The P client startup successfully.");
	}
	
	
}
