package net.automatalib.counterExamples.CounterExampleSolver.Analysis;

import net.automatalib.counterExamples.CounterExampleSolver.CounterExampleSolver;
import net.automatalib.counterExamples.CounterExampleSolver.CounterExampleTree;
import net.automatalib.counterExamples.CounterExampleSolver.GraphGenerator.GraphGenerator;
import net.automatalib.graphs.base.DefaultCFMPS;
import net.automatalib.modelcheckers.m3c.formula.FormulaNode;
import net.automatalib.modelcheckers.m3c.formula.parser.M3CParser;
import net.automatalib.modelcheckers.m3c.formula.parser.ParseException;
import net.automatalib.modelcheckers.m3c.solver.M3CSolver;
import net.automatalib.modelcheckers.m3c.solver.M3CSolvers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SuperSolverAnalysis {

    static Logger logger = LoggerFactory.getLogger(SuperSolverAnalysis.class);

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ParseException {
        GraphGenerator graphGenerator = new GraphGenerator(false);
        //set up formulas under test
        ArrayList<String> formulas = new ArrayList<>();
        formulas.add("mu X.((<b>true) || <>X)");
        formulas.add("mu X.((<b><b>true) || <>X)");
        formulas.add("mu X.((<b><b><b>true) || <>X)");
        formulas.add("mu X.((<b><b><b><b>true) || <>X)");
        formulas.add("mu X.((<b><b><b><b><b>true) || <>X)");
        formulas.add("mu X.((<b><b><b><b><b><b>true) || <>X)");
        formulas.add("mu X.((<b><b><b><b><b><b><b>true) || <>X)");
        formulas.add("mu X.((<b><b><b><b><b><b><b><b>true) || <>X)");

        for(int i = 5 ; i<graphGenerator.cfmpsList.size(); i++){ // i represents the graph in GraphData
            //System.out.println("Starting on graph " +i);
            PrintWriter writer = new PrintWriter(new FileOutputStream(
                    new File(System.getProperty("user.dir")+"//Q_"+i+".txt"),
                    true /* append = true */));
            DefaultCFMPS<String, Void> cfmps = graphGenerator.cfmpsList.get(i);
            int count=0;
            for(String f: formulas){
                System.out.println("next formula");
                testFormulaLengthWithExpandingB(cfmps, f, count, writer);
                count++;
            }
            writer.close();
        }
    }


    public static void testFormulaLengthWithExpandingB(DefaultCFMPS cfmps, String fGoal, int iter, PrintWriter writer) throws ParseException {
        try{
            int averageAmount=5;
            int iteration = iter;
            double calcM3C = 0;
            double calcTree = 0;
            double calcPath = 0;
            double height = 0;
            double size = 0;
            String path ="";
            for(int i=0; i < averageAmount; i++){
                M3CSolver.TypedM3CSolver<FormulaNode<String, Void>> m3c = M3CSolvers.typedSolver(cfmps);
                CounterExampleSolver<String, Void> ceSolver = new CounterExampleSolver<>(cfmps);
                FormulaNode<String, Void> formula = M3CParser.parse(fGoal, l -> l,
                        ap -> null);

                boolean isFulfilled = m3c.solve(formula);

                if(isFulfilled){
                    CounterExampleTree counterExampleTree = ceSolver.computeWitness(cfmps, formula);
                    path = counterExampleTree.resultString;
                    calcM3C += ceSolver.calcFulfilledTime;
                    calcTree += ceSolver.calcTreeTime;
                    calcPath += counterExampleTree.extractionTime;
                    height += counterExampleTree.maxDepth;
                    size += counterExampleTree.size();
                }
            }
            writer.append("################### Formula " + iteration + " ###################\n");

            calcM3C = (calcM3C/averageAmount)/1000000;
            calcTree = (calcTree/averageAmount)/1000000;
            calcPath = (calcPath/averageAmount)/1000000;
            writer.append((iteration+1) +",");
            writer.append(calcM3C+",");
            writer.append(calcTree+",");
            writer.append(calcPath+"\n");

            height = height/averageAmount;
            size = size/averageAmount;
            writer.append("height: " +height+"\n");
            writer.append("size: " +Math.log10(size)+"\n");
            writer.flush();
        } catch(Exception e){

        }
    }
}
