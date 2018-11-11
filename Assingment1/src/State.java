import java.util.HashMap;
import java.util.Map;


/**
 * FSA entity
 */
class State {
    String name;
    boolean isFinal;
    Map<String,State> transitions_out = null;    //all edges going out of this state
    boolean hasTransitionIN;    //are there  edges going into this state

    public State(String name, boolean isFinal){
        this.name = name;
        this.isFinal = isFinal;
        this.transitions_out = new HashMap<>();
        hasTransitionIN = false;
    }
    public State(String name){
        this.name = name;
        this.isFinal = false;
        this.transitions_out = new HashMap<>();
        hasTransitionIN = false;
    }


    /**
     * add edge going out
     */
    public void addTransitionOUT(String alpha,State next){
        transitions_out.put(alpha, next);
    }

    /**
     * mark that there is transitions going in this state
     */
    public void addTransitionIN(String alpha,State previous){
        if(!previous.name.equals(this.name)){
            hasTransitionIN = true;
        }
    }
    /**
     * check if state is disjoint
     */
    public boolean isDisjoint(){
        return !(hasTransitionIN || hasTransitionOUT());
    }

    /**
     * check if there is any edge going to another state
     */
    public boolean hasTransitionOUT(){
        for(State state:transitions_out.values()){
            if(!state.name.equals(this.name)){
                return true;
            }
        }
        return false;
    }
}
