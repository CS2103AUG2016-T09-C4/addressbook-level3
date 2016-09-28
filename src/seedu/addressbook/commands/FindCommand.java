package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;

import java.util.*;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithInformationContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }
    
    /**
     * Calls several methods to get names of all persons whose name, tags, email or phone contain any of the specified keywords
     * 
     * @param keywords
     * @return matchedPersons
     */
    private List<ReadOnlyPerson> getPersonsWithInformationContainingAnyKeyword(Set<String> keywords){
    	List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
    	
    	matchedPersons = getPersonsWithNameContainingAnyKeyword(keywords, matchedPersons);
    	matchedPersons = getPersonsWithTagsContainingAnyKeyword(keywords, matchedPersons);
    	matchedPersons = getPersonsWithPhoneNumberContainingAnyKeywords(keywords, matchedPersons);
    	matchedPersons = getPersonsWithEmailContainingAnyKeyword(keywords, matchedPersons);
    	
    	return matchedPersons;
    }

    /**
     * Retrieve all persons in the address book whose names contain any of the specified keywords 
     *
     * @param keywords for searching
     * @param matchedPersons
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords, List<ReadOnlyPerson> matchedPersons) {
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInName = new HashSet<>(person.getName().getWordsInName());
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedPersons.add(person);
            }
        }
        
        return matchedPersons;
    }

	/**
	 * Retrieve all persons in the address book whose tags are contained in any of the specified keywords
	 * 
	 * @param keywords
	 * @param matchedPersons
	 * @return list of persons found
	 */
	private List<ReadOnlyPerson> getPersonsWithTagsContainingAnyKeyword(Set<String> keywords, List<ReadOnlyPerson> matchedPersons) {
		for(ReadOnlyPerson person : addressBook.getAllPersons()){
        	if(!Collections.disjoint(person.getTags().getListOfTags(), keywords) && !matchedPersons.contains(person)){
        		matchedPersons.add(person);
        	}
        }
        
        return matchedPersons;
	}
	
	/**
	 * 
	 * Retrieves all persons in the address book whose phone numbers are contained in any of the specified keywords
	 * 
	 * @param keywords
	 * @param matchedPersons
	 * @return
	 */
	private List<ReadOnlyPerson> getPersonsWithPhoneNumberContainingAnyKeywords(Set<String> keywords, List<ReadOnlyPerson> matchedPersons){
		for(ReadOnlyPerson person: addressBook.getAllPersons()){
			if(keywords.contains(person.getPhone().toString()) && !matchedPersons.contains(person)){
				matchedPersons.add(person);
			}
		}
		
		
		return matchedPersons;
	}
	
	/**
	 * 
	 * Retrieves all persons in the address book whose emails are contained in any of the specified keywords
	 * 
	 * @param keywords
	 * @param matchedPersons
	 * @return
	 */
	private List<ReadOnlyPerson> getPersonsWithEmailContainingAnyKeyword(Set<String> keywords, List<ReadOnlyPerson> matchedPersons){
		for(ReadOnlyPerson person: addressBook.getAllPersons()){
			if(keywords.contains(person.getEmail().value) && !matchedPersons.contains(person)){
				matchedPersons.add(person);
			}
		}
		
		return matchedPersons;
	}

}
