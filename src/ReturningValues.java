import java.util.List;
import java.util.Map;

//this class is used by the search algorithms to return 2 different types of value
public class ReturningValues {

    //all the path explored by the algorithm
    private List<Block> path;
    //for each block, the parent that generated it
    private Map<Block, Block> parents;

    public ReturningValues(List<Block> path, Map<Block, Block> parents){
        this.path = path;
        this.parents = parents;
    }

    public List<Block> getPath() {
        return path;
    }

    public Map<Block, Block> getParents() {
        return parents;
    }

    public int getExploredNodes() {
        return this.path.size();
    }
}
