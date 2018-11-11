import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

//Created by Evgenia Kivotova BS17-8
//Innopolis University, 2018

public class Main {
    static String error_main = "";
    static FSA fsa;

    public static void main(String[] args) throws FileNotFoundException {

        fsa = new FSA();
        initFSA("fsa.txt");
        report();

    }

    /**
     * build fsa from settings file
     * @return 0 if there were not any error
     * @throws FileNotFoundException
     */
    public static int initFSA(String initFile) throws FileNotFoundException {
        List<String> states = new ArrayList<>();
        List<String> alpha = new ArrayList<>();
        String[] transitions = null;
        List<String> finalStates = new ArrayList<>();
        String initial = "";
            //Initialise reader and error/warnings list
            Scanner in = new Scanner(new File(initFile));


            while(in.hasNextLine()){
                String line = in.nextLine();
                //if line is not correct, exit with error
                if (!((line.contains("{"))&(line.contains("}"))&(line.contains("="))&
                        (line.indexOf('=')<line.indexOf('{')) & (line.indexOf('{')<line.indexOf('}')))){
                    error_main = "E5: Input file is malformed";
                    return -1;
                }
                else {
                    //define first word, place data at specific list
                    String command = line.substring(0, line.indexOf('='));
                    if (command.equals("states")) {
                        states = Arrays.asList(line.substring(line.indexOf('{')+1, line.indexOf('}')).split(","));
                    }
                    else if(command.equals("alpha")){
                        alpha = Arrays.asList(line.substring(line.indexOf('{')+1, line.indexOf('}')).split(","));
                    }
                    else if(command.equals("init.st")){
                        initial = line.substring(line.indexOf('{')+1, line.indexOf('}'));
                    }
                    else if(command.equals("fin.st")){
                        finalStates = Arrays.asList(line.substring(line.indexOf('{')+1, line.indexOf('}')).split(","));
                    }
                    else if(command.equals("trans")){
                        transitions = line.substring(line.indexOf('{')+1, line.indexOf('}')).split(",");
                    }
                    else{
                        //if fird word is strange, line is incorrect
                        error_main = "E5: Input file is malformed";
                        return -1;
                    }
                }
            }
            in.close();
        //fill fsa with states
        for (String state:states) {
            int error = fsa.addState(state);
            if (error == -1){
                error_main = "E5: Input file is malformed";
                return -1;
            }
        }
        //fill fsa with alphabet
        for (String item:alpha) {
            int error = fsa.addInAlphabet(item);
            if (error == -1){
                error_main = "E5: Input file is malformed";
                return -1;
            }
        }
            //set initial state
            if(initial.equals("")){
                error_main = "E4: Initial state is not defined";
                return -1;
            }
            int error = fsa.setInitial(initial);
            if(error == -1){
                error_main = "E1: A state '"+initial+"' is not in set of states";
                return -1;
            }

            //set final states

            for (String state : finalStates) {
                    error = fsa.addFinal(state);
                    if (error == -1) {
                        error_main = "E1: A state '"+state+"' is not in set of states";
                        return -1;
                    }
            }


            //fill fsa with  transitions
            for (String transition:transitions){
                String[] params = transition.split(">");
                if(params.length==3){
                    error = fsa.addTransition(params[0],params[1],params[2]);
                    if(error == -1){
                        error_main = "E1: A state '"+params[0]+"' is not in set of states";
                        return -1;
                    }
                    if(error == -11){
                        error_main = "E1: A state '"+params[2]+"' is not in set of states";
                        return -1;
                    }
                    if(error == -2){
                        error_main = "E3: A transition '"+params[1]+"' is not represented in the alphabet";
                        return -1;
                    }
                    if(error == -3){
                        error_main = "E6: FSA is nondeterministic";
                        return -1;
                    }
                }
                else{
                    error_main = "E5: Input file is malformed";
                    return -1;
                }
            }

            if(fsa.isDisjoint()){
                error_main = "E2: Some states are disjoint";
            }


        return 0;
    }

    /**
     * output report about fsa
     * @throws FileNotFoundException
     */
    public static void report() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("result.txt");
        Map<String,String>errors_dscrptn = new HashMap<String,String>();
        Map<String,String>warnings_dscrptn = new HashMap<String,String>();

        errors_dscrptn.put("E1","E1: A state s is not in set of states");
        errors_dscrptn.put("E2","E2: Some states are disjoint");
        errors_dscrptn.put("E3","E3: A transition a is not represented in the alphabet");
        errors_dscrptn.put("E4","E4: Initial state is not defined");
        errors_dscrptn.put("E5","E5: Input file is malformed");

        //print error if exist
        if(!error_main.equals("")){
            out.print("Error:");
            out.print("\n"+error_main);
        }
        else out.print(fsa.toRegularExp());
        out.close();
    }

}



