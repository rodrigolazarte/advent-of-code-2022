package day12;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class HillClimbing {
    private static char[][] hillMap;
    private static Node startNode, goalNode, currentNode;
    private static Node[][] nodes;
    private static boolean goalReached = false;
    private static final ArrayList<Node> checkedList = new ArrayList<>();
    private static final ArrayList<Node> openList = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        hillMap = createHillMap("src/day12/input.txt");
        mapHillToNodes();
        setStartNode();
        setGoalNode();
        setCostsOnNodes();
        search();
        printMap();
    }

    private static char[][] createHillMap(String inputUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(inputUrl)).useDelimiter("\n");
        int rows = 0;

        while (scanner.hasNext()) {
            rows++;
            scanner.next();
        }

        //Reading file again
        scanner = new Scanner(new FileReader(inputUrl)).useDelimiter("\n");

        char[][] hillMap = new char[rows][];
        int rowNumber = 0;

        while (scanner.hasNext()) {
            char[] row = scanner.next().trim().toCharArray();
            hillMap[rowNumber] = row;
            rowNumber += 1;
        }

        return hillMap;
    }

    private static void mapHillToNodes() {
        nodes = new Node[hillMap.length][hillMap[0].length];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                nodes[i][j] = new Node(hillMap[i][j], i, j);
            }
        }
    }

    private static void setStartNode() {
        for (Node[] listOfNodes: nodes) {
            for (Node node: listOfNodes) {
                if (node.getValue() == 'S') {
                    node.setStart(true);
                    startNode = node;
                    currentNode = startNode;
                    return;
                }
            }
        }
    }

    private static void setGoalNode() {
        for (Node[] listOfNodes: nodes) {
            for (Node node: listOfNodes) {
                if (node.getValue() == 'E') {
                    node.setGoal(true);
                    goalNode = node;
                    goalNode.setSolid(true);
                    return;
                }
            }
        }
    }

    private static void setCostsOnNodes() {
        int maxRow = nodes.length;
        int maxColumn = nodes[0].length;
        int col = 0;
        int row = 0;

        while (row < maxRow && col < maxColumn) {
            getCost(nodes[row][col]);
            col++;
            if (col == maxColumn) {
                col = 0;
                row++;
            }
        }
    }

    private static void getCost(Node node) {
        int xDistance = Math.abs(node.getColumn() - startNode.getColumn());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());

        node.setgCost(xDistance + yDistance);

        xDistance = Math.abs(node.getColumn() - goalNode.getColumn());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());

        //Manhattan distance
        node.sethCost(xDistance + yDistance);

        //Chevysheb distance
        //node.sethCost(Math.max(xDistance, yDistance));

        //Euclidian distance
        //node.sethCost(Math.hypot(yDistance, xDistance));

        node.setfCost(node.getgCost() + node.gethCost());
    }

    private static void search() {
        while (!goalReached) {
            int row = currentNode.getRow();
            int col = currentNode.getColumn();

            currentNode.setChecked(true);
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //OPEN THE DOWN NODE
            if (row + 1 < nodes.length){
                evaluateIfNodeIsReachable(nodes[row + 1][col]);
                openNode(nodes[row + 1][col]);
            }

            //OPEN THE RIGHT NODE
            if (col + 1 < nodes[0].length){
                evaluateIfNodeIsReachable(nodes[row][col + 1]);
                openNode(nodes[row][col + 1]);
            }

            //OPEN THE LEFT NODE
            if (col - 1 >= 0){
                evaluateIfNodeIsReachable(nodes[row][col - 1]);
                openNode(nodes[row][col - 1]);
            }

            //OPEN THE UP NODE
            if (row - 1 >= 0) {
                evaluateIfNodeIsReachable(nodes[row - 1][col]);
                openNode(nodes[row - 1][col]);
            }


            int bestNodeIndex = 0;
            double bestNodefCost = 99999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).getfCost() < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).getfCost();
                } else if (openList.get(i).getfCost() == bestNodefCost) {
                    if (openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()) {
                        bestNodeIndex = i;
                    }
                }
            }

            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                System.out.println(trackThePath());
            }
        }
    }

    private static void openNode(Node node) {
        if (!node.isOpen() && !node.isChecked() && !node.isSolid()) {

            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    private static void evaluateIfNodeIsReachable(Node node) {
        char destinyValue = node.getValue();
        char currentValue = currentNode.getValue();
        if (currentNode.isStart() && destinyValue == 'a') {
            node.setSolid(false);
        }
        if (!(destinyValue > (char)(currentValue + 1))) {
            node.setSolid(false);
        }
        if (destinyValue == 'E') {
            node.setSolid(true);
            if (currentValue == 'z') {
                node.setSolid(false);
            }
        }
    }

    private static String trackThePath() {
        Node current = goalNode;
        StringBuilder finalPath = new StringBuilder();

        while (current != startNode) {
            current = current.getParent();
            if (current != startNode) {
                current.setAsPath();
                finalPath.append(current.getValue());

                hillMap[current.getRow()][current.getColumn()] = '0';
            }
        }

        return finalPath.reverse().append(goalNode.getValue()).toString();
    }

    private static void printMap() {
        for (char[] row: hillMap) {
            for (char value: row) {
                System.out.print(value);
            }
            System.out.print("\n");
        }
    }
}
