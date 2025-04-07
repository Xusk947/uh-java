package wars; 


/**
 * This class records information about student contributions to
 * the project.
 * 
 * @version 19/02/25
 */
public class Teamwork
{
    private String[] details = new String[10];
    
    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = "CS90";
        
        details[1] = "Arzamastsev";
        details[2] = "Oleksandr";
        details[3] = "23022417";

        details[4] = "Kasimkhodja";
        details[5] = "Farrukh";
        details[6] = "23000664";

        details[7] = "Nabiev";
        details[8] = "Aziz";
        details[9] = "23010223";
    }
    
    public String[] getTeamDetails()
    {
        return details;
    }
    
    public void displayDetails()
    {
        for(String temp:details)
            {
            System.out.println(temp.toString());
            }
        }
}
