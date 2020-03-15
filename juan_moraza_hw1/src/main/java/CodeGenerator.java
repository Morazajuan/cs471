
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/*Originially try to implement eclipse JDT but not successful*/
//import org.eclipse.core.commands.AbstractHandler;
//import org.eclipse.core.commands.ExecutionEvent;
//import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.core.resources.IFolder;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.resources.IWorkspace;
//import org.eclipse.core.resources.IWorkspaceRoot;
//import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.jdt.core.IJavaProject;
//import org.eclipse.jdt.core.IPackageFragment;
//import org.eclipse.jdt.core.IPackageFragmentRoot;
//import org.eclipse.jdt.core.JavaCore;
//import org.eclipse.jdt.core.JavaModelException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class

/**
 *   add this to sbt file
 *     "org.eclipse.core" % "org.eclipse.core.resources" % "3.6.0.v20100526-0737",
 *     "org.eclipse.core" % "runtime" % "3.10.0-v20140318-2214",
 *     "org.eclipse.core" % "commands" % "3.3.0-I20070605-0010",
 *     "org.eclipse.jdt" % "org.eclipse.jdt.core" % "3.19.0",
 ***/

/**
 *
 * Getting Started:
 *
 * ✓ Create account in BitBucket with UIC email
 * ✓ Change bitbucket from standard -> Academic
 * ✓ Join team in BitBucket (CS474_Spring2020)
 * ✓ Make sure to have IntelliJ, JDK, Scala Runtime and Scala Plugin
 * ✓ Get SBT or Gradle tool
 * ✓ Create, Compile & run Java & Scala Program
 * ✓ Use logging(use Logback and SLFL4J) and configuration management frameworks(Typesafe Configuration Library)
 * ✓ import build.sbt or gradle script libraries
 * ✓ Comment code and supply logging statements
 * Input and configuration variables must be supplied to configuration files
 * Create an object-oriented design and implementation of a program that generates the implementation code of the design pattern
 *
 **/
// Intellij plugins installed installed
//JDT AstView
//Scala
// To enable, either: (a) select "Enable JDT AST View" under the
// "Tools" menu, or (b) right-click on an editor and select "Enable JDT AST View" in the popup menu.


public class CodeGenerator  {

    /**This commented code was for eclipse it would run but i didnt know how to proceed.**/
//public class CodeGenerator extends AbstractHandler  {
//    @Override
//    public Object execute(ExecutionEvent event) throws ExecutionException {
//        IWorkspace workspace = ResourcesPlugin.getWorkspace();
//        IWorkspaceRoot root = workspace.getRoot();
//        // Get all projects in the workspace
//        IProject[] projects = root.getProjects();
//        // Loop over all projects
//        for (IProject project : projects) {
//            try {
//                // only work on open projects with the Java nature
//                if (project.isOpen()
//                        & project.isNatureEnabled(JavaCore.NATURE_ID)) {
//                    createPackage(project);
//                }
//            } catch (CoreException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        return null;
//    }
//    private void createPackage(IProject project) throws JavaModelException {
//        IJavaProject javaProject = JavaCore.create(project);
//        IFolder folder = project.getFolder("src");
//        // folder.create(true, true, null);
//        IPackageFragmentRoot srcFolder = javaProject
//                .getPackageFragmentRoot(folder);
//        IPackageFragment fragment = srcFolder.createPackageFragment(
//                project.getName(), true, null);
//    }




    private static final Logger log = LoggerFactory.getLogger("CodeGenerator");

    public static void main(String[] args) throws IOException {
        // {{start:resource}}
        Config defaultConfig = ConfigFactory.parseResources("defaults.conf");
        // {{end:resource}}

        //Programing Laguanges to pick from
        String[] languages = {"java", "c"};

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Type the programing laguange you want to use for this code generator:\n\tJava\n\tC\n>");

        //REads line to pick programing language
        String input = reader.readLine();
        input.toLowerCase();
        while(true){
            if((input.compareTo(languages[0]) == 0)|| (input.compareTo(languages[1]) == 0) ){
                System.out.println("You have picked: " + input);

                break;
            }else{
                System.out.print("Not valid option. Try inputting one of the options\n\tJava\n\tC\n>");
                input = reader.readLine();
                input.toLowerCase();
            }
        }
        log.info("User has picked {} language",input);
        String language = input;
        String patterns[] ={
                "Abstract Factory Pattern",
                "Builder Pattern",
                "Facade Pattern",
                "Chain of Responsibility Pattern",
                "Mediator Pattern",
                "Visitor Pattern",
                "Template method Pattern"
        };

        //prints all patterns
        System.out.println("Pick the # of Pattern you want to use :");
        for(int i =0; i < patterns.length; i++){
            System.out.println(i+": "+patterns[i]);
        }
        //promts to enter one option #
        System.out.print(">");
        input = reader.readLine();
        int option;
        try{
             option = Integer.valueOf(input);
        }catch (Exception e){
             option = -1;
            System.out.println("not a number :/");

        }

        log.info("User has picked {} language",patterns[option]);

        //will exit until option is valid
        while(true){
            if(option>= 0 && option <= 6){
                System.out.println("You have picked option: " + option +". "+patterns[option]);

                break;
            }else{
                System.out.print("Not valid option. Try inputting one of the options listed below:\n");
                for(int i =0; i < patterns.length; i++){
                    System.out.println(i+": "+patterns[i]);
                }
                input = reader.readLine();
                try{
                    option = Integer.valueOf(input);
                }catch (Exception e){
                    option = -1;
                    System.out.println("not a number :/");

                }
            }
        }



        //this is at the end
        String FILE_NAME = "CodeGenerated/main."+language;

        //Lets create file
        try {
            File myObj = new File(FILE_NAME);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("Main File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        String numMethods;
        String methodNames[];
        String typeOfMethod[];
        String numArgument;
        String arguments[][];
        String method;
        String body[];
        String bodyLine ="";

        //write to file
        try {
            FileWriter myWriter = new FileWriter(FILE_NAME);
            if(language.compareTo("java")==0)
                myWriter.write(defaultConfig.getString(language+".main.class"));


            System.out.print("Number of methods for this class:");
            numMethods= reader.readLine();
            methodNames = new String[Integer.valueOf(numMethods)];
            typeOfMethod = new String[Integer.valueOf(numMethods)];
            arguments = new String[Integer.valueOf(numMethods)][];
            body = new String[Integer.valueOf(numMethods)];

            for(int i =0; i<Integer.valueOf(numMethods); i++){
                System.out.print("Name Method "+i+":");
                method = reader.readLine();
                methodNames[i] = method;
                System.out.print("Type of Method(int, void, Node, etc...) for "+method+": " );
                typeOfMethod[i] = reader.readLine();;
                System.out.print("how many arguments for : "+method );


                numArgument = reader.readLine();
                System.out.print("Type type whole argument(int val) of for "+method +": " );
                arguments[i] =  new String[Integer.valueOf(numArgument)];

                for(int j =0; j< Integer.valueOf(numArgument); j++){
                    System.out.print("\n>");
                    arguments[i][j] = reader.readLine();

                }

                System.out.print("\nType  body for "+method+". Type(-1) in single line to end body \n>"  );
                body[i] = "";
                while(bodyLine.compareTo("-1") != 0){
                    bodyLine = reader.readLine();
                    if(bodyLine.compareTo("-1") != 0 ){

                        body[i] += bodyLine+"\n";

                    }
                }

                bodyLine = "";

            }
            log.info("User has Picked {}  ",patterns[option]);

            for(int i = 0; i < methodNames.length; i++){
                log.info("User has created method {} ",methodNames[i]);
                log.info("\tType: ",typeOfMethod[i]);
                log.info("\tWith following Argument: ",typeOfMethod[i]);

                for(int j = 0; j < arguments[i].length; j++){
                    log.info("\t\t ",arguments[i][j]);
                }


            }
            //write each method before main
            for(int i = 0; i <methodNames.length; i++ ){
                myWriter.write(typeOfMethod[i]+" "+methodNames[i]);
                //argument
                myWriter.write("(");

                for( int j = 0; j < arguments[i].length; j++){


                    myWriter.write(arguments[i][j]);
                    if(j != arguments[i].length -1){
                        myWriter.write(",");
                    }

                }

                //start of body method
                myWriter.write("){\n");
                myWriter.write(body[i]);

                //end of body method
                myWriter.write("}\n\n");


            }


            //main body
            myWriter.write("//class:"+defaultConfig.getString(language+"."+option+".name"));

            System.out.println("Type your main function to call pattern");
            System.out.println("Type line by line and when you are done in a new line type -1");
            String bodyMain = reader.readLine();

            while(bodyLine.compareTo("-1") != 0){
                bodyLine = reader.readLine();
                if(bodyLine.compareTo("-1") != 0 ){

                    bodyMain += (bodyLine+"\n");

                }
            }
            myWriter.write(defaultConfig.getString(language+".main.start"));

            myWriter.write(bodyMain);





                myWriter.write(defaultConfig.getString(language+".main.end"));

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        // {{start:text}}


    }

}
