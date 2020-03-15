package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;

import com.intellij.openapi.vfs.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

import java.awt.*;
import java.io.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class CreateFile  extends AnAction{
    private static final Logger log = LoggerFactory.getLogger(CreateFile.class);


    public CreateFile() {
        super();
    }

    public char detectFileExt(String str){

        if(str.charAt(str.length()-1) == 'c'){
            return 'c';
        }else if(str.charAt(str.length()-4) == 'j' &&
                str.charAt(str.length()-3) == 'a' &&
                str.charAt(str.length()-2) == 'v' &&
                str.charAt(str.length()-1) == 'a' ){

            return 'j';
        }else{
            return 'e';
        }
    }
    //write file
    public void writeFile(Config config, String FILE_NAME, String pattern, String language,
                          String methodNames[],String typeOfMethod[],String arguments[][],
                          String body[], String mainBody){

        //write to file
        try {
            FileWriter myWriter = new FileWriter(FILE_NAME);
            if(language.compareTo("j")==0 || language.compareTo("c") == 0)
                myWriter.write(config.getString("conf."+language+".main.class"));
            else return;

            log.info("Starting writing {} file", config.getString("conf."+language+".name"));


            //write each method before main
            for(int i = 0; i <methodNames.length; i++ ){
                myWriter.write(typeOfMethod[i]+" "+methodNames[i]);//start writing methods
                //argument
                myWriter.write("(");//then arguments

                for( int j = 0; j < arguments[i].length; j++){

                    myWriter.write(arguments[i][j]);
                    if(j != arguments[i].length-1){//one before last one to not put extra comma
                        myWriter.write(",");
                    }

                }

                //close arguments and open method body
                myWriter.write("){\n");

                myWriter.write(body[i]);//method ith body

                //close body method
                myWriter.write("}\n\n");//if any then, loop for next method

            }


            //main body depending file extension
            myWriter.write("//class:"+config.getString("conf."+language+"."+pattern+".name"));

            System.out.println("Type your main function to call pattern");
            System.out.println("Type line by line and when you are done in a new line type -1");

            myWriter.write(config.getString("conf."+language+".main.start"));

            myWriter.write(mainBody);

            myWriter.write(config.getString("conf."+language+".main.end"));

            myWriter.close();

            log.info("Successfully wrote to the file.");
        } catch (IOException e) {
            log.error("An error occurred while writing code into file.");
            e.printStackTrace();
        }
    }
    public String OSPath(String path, String file, int OS){
            if(OS == 0)
                return new String(path+"/"+file);
            else
                return new String(path+"\\"+file);

    }

    public  String getPatternName(int num){
        String patterns[] ={
                "Abstract Factory Pattern",
                "Builder Pattern",
                "Facade Pattern",
                "Chain of Responsibility Pattern",
                "Mediator Pattern",
                "Visitor Pattern",
                "Template methodName Pattern"
        };

        return patterns[num];

    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent event)  {
//        Config defaultConfig = ConfigFactory.load("application.conf");

        //Episode 1 tutorial of how to do plugin video. Linked on the hw description
        FileChooserDescriptor fileChooserDes = new FileChooserDescriptor(false, true, false, false, false, false);

        fileChooserDes.setTitle("Directory");
        fileChooserDes.setDescription("pick Directory");
        log.info("User has selected option to generate code");



        //VirtualFile is responsible to get path from chosen folder to later
        //create file in it
        VirtualFile files = FileChooser.chooseFile(fileChooserDes, (Component) null, null, null);
        if(files.getPath()!=null) {
            String filePath = files.getPath();
            String fileName;
            String language = "";


            Messages.showMessageDialog((Project) null, files.getPath(), "File's Path", Messages.getInformationIcon());
            log.info("User has picked {} to create new file", filePath);


            fileName = Messages.showInputDialog(event.getProject(), "Type name and extension as well\neg. file.c", "Type name for File:", Messages.getInformationIcon());

            log.info("Name of file chosen {}", fileName);


            Messages.showMessageDialog(fileName+"\ncreated at: \n"+filePath, "user has created: ", null  );


            String path = OSPath(filePath,fileName, 0); //<------ DEPENDS on OS. If is mac it should be / if is windows then should be \
            //CREATE File
            try {
                File myObj = new File(path);

                 if (myObj.createNewFile()) {
                    log.info("File created: " + myObj.getName());
                } else {
                    log.info("File already exists.");
                }
            } catch (IOException e) {
                 log.error("The file creation failed");

                e.printStackTrace();
            }


            Config config = null;
            //Locate and read conf file
            try {//Code guided from Calc in piazza
                InputStream inStream = CreateFile.class.getClassLoader()
                        .getResourceAsStream("application.conf");

                if (inStream != null) {
                    Reader reader = new InputStreamReader(inStream);
                    // Load the configuration file
                      config = ConfigFactory.parseReader(reader);
                    log.info("Conf File name {} SUCCESSFULLY READ :)", config.getString("conf.name"));
                    inStream.close();


                    //detect file extension
                    if( (detectFileExt(fileName) == 'c') || (detectFileExt(fileName) =='j'))
                    {
                        language =  Character.toString(detectFileExt(fileName));
                        log.info("File extension {} detected as a {} file",detectFileExt(fileName) , config.getString("conf."+detectFileExt(fileName)+".name") );
                    }else{
                        log.error("Invalid extension inputted");

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info(config.getString("conf.j.main.class"));


            //Select pattern
            String patternType  = Messages.showInputDialog(event.getProject(),
                    "Enter number of pattern desired\n" +
                    "0. Abstract Factory Pattern\n" +
                    "1. Builder Pattern\n" +
                    "2. Facade Pattern\n" +
                    "3. Chain of Responsibility Pattern\n" +
                    "4. Mediator Pattern\n" +
                    "5. Visitor Pattern\n" +
                    "6. Template methodName Pattern\n",
                    "Select Pattern Type", Messages.getInformationIcon());

            log.info("User has picked {} ", config.getString("conf."+language+".main.start") );

            //Obtain data for our file

            String patternName = getPatternName( Integer.valueOf(patternType));
           //--how many methods
            String numMethods  = Messages.showInputDialog(event.getProject(), "User has picked "+patternName+".\nEnter number of methods for this class:\n" , "How many methods?\n"  , Messages.getInformationIcon());
            String methodNames[] = new String[Integer.valueOf(numMethods)];
            String typeOfMethod[] = new String[Integer.valueOf(numMethods)];
            log.info("User has picked {} methods to implement ", Integer.valueOf(numMethods) );

            //
            String numArgument;
            String arguments[][];
            String methodName;
            String methodType;
            String bodyLine ="";
            String body[] = new String[Integer.valueOf(numMethods)];


            arguments = new String[Integer.valueOf(numMethods)][];

            for(int i = 0; i < methodNames.length; i++){

                methodName  = Messages.showInputDialog(event.getProject(), "Enter name for method "+(i+1)+" (just name, no type):\n" , "Method Name"  , Messages.getInformationIcon());
                log.info("User has created  {} method",methodName);
                //added to the array
                methodNames[i] = methodName;

                //type of method
                methodType  = Messages.showInputDialog(event.getProject(), "Enter Type for "+methodName+" method:\neg. int, void, Node, etc..\n" , "Method Type"  , Messages.getInformationIcon());
                typeOfMethod[i] = methodType;
                log.info("User has pick type {} for method {} ",methodType, methodName);

                //how many arguments for method
                numArgument  = Messages.showInputDialog(event.getProject(), "Enter number of argument for this method. \n","Number of Arguments"  , Messages.getInformationIcon());
                log.info("User has pick {} arguments for method {} ", Integer.valueOf(numArgument), methodName);
                arguments[i] = new String[Integer.valueOf(numArgument)];


                //loop through arguments for ith method
                String argument;
                for(int j =0; j< Integer.valueOf(numArgument); j++){
                    argument  = Messages.showInputDialog(event.getProject(), "Type whole argument "+ (j+1)+" for method "+ methodNames[i]+": \nInclude their type too\neg. int num, Node hd, etc\n ",(j+1)+ " Argument "  , Messages.getInformationIcon());
                    arguments[i][j] = argument;
                }


                //Type body for method ith
                bodyLine  = Messages.showInputDialog(event.getProject(), "\nType  body for method "+ methodNames[i] +". Type (-1) to end body\n","Method Body"  , Messages.getInformationIcon());
                body[i] += bodyLine;
                while(bodyLine.compareTo("-1") != 0){
                    bodyLine  = Messages.showInputDialog(event.getProject(), "\nContinue typing  body for method"+ methodNames[i] +". Type(-1) in single line to end body \n","Method Body"  , Messages.getInformationIcon());
                    if(bodyLine.compareTo("-1") == 0)
                        break;
                    body[i] += bodyLine;
                }

            }//end loop of all methods

            //Type main body
            String mainBodyLine  = Messages.showInputDialog(event.getProject(), "\nType  body for MAIN. \nType (-1) to end body\n","Main Body"  , Messages.getInformationIcon());

            String mainBody = mainBodyLine;

            while(mainBodyLine.compareTo("-1") != 0){
                mainBodyLine  = Messages.showInputDialog(event.getProject(), "\nContinue typing  body for MAIN. \nType(-1) in single line to end body \n","Main Body"  , Messages.getInformationIcon());
                if(mainBodyLine.compareTo("-1") == 0)
                    break;
                mainBody  += mainBodyLine;
            }

            //log info for file
            //method names, method type, arguments
            for(int i = 0; i < methodNames.length; i++){
                log.info("User has created  {} method",methodNames[i]);
                log.info("\tType: {}",typeOfMethod[i]);
                log.info("\tWith following Argument: ");

                for(int j = 0; j < arguments[i].length; j++){
                    log.info("\t\t {}",arguments[i][j]);
                }

            }

            //write date into file
            writeFile( config, path, patternType,  language, methodNames, typeOfMethod, arguments, body,  mainBody);

        }

    }

    @Override
    public void update(AnActionEvent e) {
        // Set the availability based on whether a project is open
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }
}
