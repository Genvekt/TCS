import java.util.*;


class FSA {
    Map<String,State> states;
    State initial;
    List<String> alphabet;

    public FSA(){
        this.states = new HashMap<String,State>();
        this.initial = null;
        this.alphabet = new ArrayList<>();

    }

    /**
     * Adds state into fsa
     * @param state - name of a state
     * @return -1 if the state already exist, 0 otherwise
     */
    public int addState(String state){
        if(states.containsKey(state)){
            return -1;
        }
        else{
            State newOne = new State(state);
            states.put(state, newOne);
            return 0;
        }
    }
    /**
     * Adds final state into fsa
     * @param state - name of a state
     * @return -1 if the state does not exist, 0 otherwise
     */
    public int addFinal(String state){
        if(!states.containsKey(state)){
            return -1;
        }
        else {
            states.get(state).isFinal = true;
            return 0;
        }
    }

    /**
     * Adds initial state into fsa
     * @param state - name of a state
     * @return -1 if the state does not exist, 0 otherwise
     */
    public int setInitial(String state){
        if(!states.containsKey(state)){
            return -1;
        }
        else {
            initial = states.get(state);
            return 0;

        }
    }

    /**
     * Adds final state into fsa
     * @param state1 - name of a state from
     * @param alpha - name of a transition
     * @param state2 - name of a state to
     * @return -1 if the state1 does not exist
     *         -11 if the state2 does not exist
     *         -2 if the alpha does not exist
     *         -3 if transition with this name from state1 to another state already exist
     *         0 otherwise
     */
    public int addTransition(String state1, String alpha, String state2){
        //if there is no state1 in states
        if(!states.containsKey(state1)){
            return -1;
        }
        //if there is no state2 in states
        if(!states.containsKey(state2)){
            return -11;
        }
        //if there is no alpa in alphabet
        if(!alphabet.contains(alpha)){
            return -2;
        }
        //if transition with this name already exists
        if(states.get(state1).transitions_out.containsKey(alpha) && !states.get(state1).transitions_out.get(alpha).name.equals(states.get(state2).name)){
            return -3;
        }
        else{
            states.get(state1).addTransitionOUT(alpha,states.get(state2));
            states.get(state2).addTransitionIN(alpha,states.get(state1));
            return 0;
        }
    }

    /**
     * Add entity into alphabet
     * @param item -  name of transition
     * @return -1 if transition with this name already exist, 0 otherwise
     */
    public int addInAlphabet(String item){
        if(alphabet.contains(item)){
            return -1;
        }
        else{
            alphabet.add(item);
            return 0;
        }
    }

    /**
     *check if there is any disjoint states in fsa
     */
    public boolean isDisjoint(){
        if(states.size()>1) {
            for (State state : states.values()) {
                if (!state.name.equals(initial.name) && state.isDisjoint()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *check if all states are reacheble from initial state
     */
    public int isAllStatesReacheble(){
        if(states.size()>1) {
            for (State state : states.values()) {
                if (!state.name.equals(initial.name) && !state.hasTransitionIN) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     *check completeness of fsa
     */
    public boolean isComplete(){
        for(State state:states.values()){
            if(state.transitions_out.size()<alphabet.size()){
                return false;
            }
        }
        return true;
    }

    public boolean isAccepted(String[] commands){
        State current = initial;
        for(String command:commands){
            if(current.transitions_out.containsKey(command)){
                current = current.transitions_out.get(command);
            }
            else return false;
        }
        if(current.isFinal){
            return true;
        }
        else return false;
    }

    public String toRegularExp(){
        return(" I working on it)");
    }


}
