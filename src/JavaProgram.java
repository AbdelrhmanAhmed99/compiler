import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class JavaProgram {

    static String abdo_file_name ;
    static String abdo_java ;
    static String outdir ;

    public static  void main(String[] args) throws Exception {
                int i = 1;
                while (i<5){
                    processor("test/test_"+i+".java",""+i);
                    i++;
                }
            }




    /**
	 * {@inheritDoc}
	 *
	 * <p>PARSING SOURCE TO TOKEN AND GENERATE THE PARSING TREE</p>
     * @param input CharStream
     * 
	 */
    public static  MyJavaListener generateAndWalkParserTree(CharStream input ) {
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        MyJavaListener extractor = new MyJavaListener(tokens);
        walker.walk(extractor, tree); // initiate walk of tree with listener

        return extractor ;
    }


    /**
	 * {@inheritDoc}
	 *
	 * <p>INSERCT JAVA MODIFICATIONS</p>
     * @param extractor MyJavaListener 
     * @param testNumber String
     * 
	 */
    public static File  generateJavaInjectionCode (MyJavaListener extractor , String testNumber)
            throws IOException
    {
        // abdo file and directory
         abdo_file_name = "abdo_" + testNumber;
         abdo_java = abdo_file_name + ".java";
         outdir = "abdo/";

        // write the answer to file
        File abdoJavaFile = new File(outdir + abdo_java);
        if (!abdoJavaFile.createNewFile()) {
            boolean res = abdoJavaFile.delete();
            abdoJavaFile = new File(outdir + abdo_java);
        }
        FileWriter myWriter = new FileWriter(outdir + abdo_java);
        String javaabdo = extractor.rewriter.getText()
                .replace("test", "abdo") ;
        myWriter.write(javaabdo);
        myWriter.close();

        return  abdoJavaFile ;
    }

     /**
	 * {@inheritDoc}
	 *
	 * <p>RUN THE INSERCTED JAVA MODIFICATIONS AND INDCATE THE VISITED LINES</p>
     * @param abdoJavaFile File
     *
	 */
    public static BufferedReader runCode (File abdoJavaFile){

        Process theProcess = null;
        BufferedReader inStream = null;
        String abdo_file_name= abdoJavaFile.getName() ;

        abdoJavaFile.getParentFile().mkdirs();


        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, System.out, null, abdoJavaFile.getPath());
        try{
            theProcess = Runtime.getRuntime().exec("java abdo/"+abdo_file_name);
        }
        catch(IOException e){
            System.err.println("Error on exec() method");
            e.printStackTrace();
        }
        inStream = new BufferedReader(
                new InputStreamReader(theProcess.getInputStream()));

        return  inStream ;

    }

//    /**
//	 * {@inheritDoc}
//	 *
//	 * <p>CONVERT JAVA TO HTML AND INSERT PRE TAG BEFORE EVERY BLOCK </p>
//     * @param input CharStream
//     *
//	 */
    public static void writefiles (BufferedReader  inStream ,MyJavaListener extractor) throws IOException {

        File abdoHtmlFile = new File(outdir+abdo_file_name+".html") ;
        FileWriter myHtmlWriter = new FileWriter(outdir +abdo_file_name+".html" );

        myHtmlWriter.write( "<html> \n<head> \n<style>\n" +
                " body{background: chartreuse;}\n pre{background : lightsalmon}\n" ) ;
        String s = null;
        // write style of the code
        File abdoblocks = new File(outdir+"block_"+abdo_file_name+".txt");
        FileWriter coco = new FileWriter(outdir+"block_"+abdo_file_name+".txt" );
        String x = "";
        while ((s = inStream.readLine()) != null) {
            if(!s.matches(".+_[0-9]+"))
            {
                continue;
            }

            x+=s+"\n";

            System.out.println(s);
            s = "#"+s+"{background:chartreuse}\n" ;
            myHtmlWriter.write(s);
        }
        coco.write(x);
        coco.close();


        myHtmlWriter.write("</style>\n </head>\n <body>\n") ;
        myHtmlWriter.write(extractor.getRewriter2().getText().replace("\n", "<br>"));
        myHtmlWriter.write("</body> \n</html> \n") ;

        myHtmlWriter.close();


    }


    /**
	 * {@inheritDoc}
	 *
	 * <p>HELPER METHOD</p>
     * @param path String 
     * @param testNum String
     * 
	 */
    static void processor(String path, String testNum) throws IOException {
        CharStream input = CharStreams.fromFileName(path);

        MyJavaListener extractor =  generateAndWalkParserTree(input ) ;

        File abdoJavaFile =  generateJavaInjectionCode(extractor , testNum) ;

        // run the file
        BufferedReader inStream = runCode (abdoJavaFile) ;

        writefiles(inStream ,extractor) ;
    }




}
