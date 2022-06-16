import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }

  public void menu()
  {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }

  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortString(ArrayList<String> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      String temp = listToSort.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchCast()
  {
    /* TASK 4: IMPLEMENT ME! */
    System.out.print("Enter a person to search for (first or last name): ");
    String searchCast = scanner.nextLine();

    // prevent case sensitivity
    searchCast = searchCast.toLowerCase();

    ArrayList<String> castList = new ArrayList<String>();
    for (Movie movie : movies)
    {
      String[] actorList = movie.getCast().split("\\|");
      for (String cast : actorList)
      {
        String lowerCaseCast = cast.toLowerCase();
        if (lowerCaseCast.indexOf(searchCast) != -1)
        {
          castList.add(cast);
        }
      }
    }

    for (int i = 0; i < castList.size(); i++)
    {
      for (int j = i + 1; j < castList.size(); j++)
      {
        if (castList.get(i).equals(castList.get(j)))
        {
          castList.remove(j);
          j--;
        }
      }
    }

    sortString(castList);

    // now, display them all to the user
    for (int i = 0; i < castList.size(); i++)
    {
      String castMember = castList.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + castMember);
    }

    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();
    String selectedCast = castList.get(choice - 1);

    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String[] castList2 = movies.get(i).getCast().split("\\|");
      for (String movieCast : castList2)
      {
        if (movieCast.equals(selectedCast))
        {
          //add the Movie objest to the results list
          results.add(movies.get(i));
        }
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    /* TASK 3: IMPLEMENT ME! */
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieKeywords = movies.get(i).getKeywords();
      movieKeywords = movieKeywords.toLowerCase();

      if (movieKeywords.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listGenres()
  {
    /* TASK 5: IMPLEMENT ME! */
    ArrayList<String> genres = new ArrayList<String>();
    for (Movie movie : movies)
    {
      String[] genreList = movie.getGenres().split("\\|");
      for (String genre : genreList)
      {
        genres.add(genre);
      }
    }

    for (int i = 0; i < genres.size(); i++)
    {
      for (int j = i + 1; j < genres.size(); j++)
      {
        if (genres.get(i).equals(genres.get(j)))
        {
          genres.remove(j);
          j--;
        }
      }
    }

    sortString(genres);

    for (int i = 0; i < genres.size(); i++)
    {
      String genre = genres.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + genre);
    }

    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");


    int choice = scanner.nextInt();
    scanner.nextLine();
    String selectedGenre = genres.get(choice - 1);

    ArrayList<Movie> results = new ArrayList<Movie>();
    for (int i = 0; i < movies.size(); i++)
    {
      String genreName = movies.get(i).getGenres();
      if (genreName.indexOf(selectedGenre) != -1)
      {
        results.add(movies.get(i));
      }
    }

    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRated()
  {
    /* TASK 6: IMPLEMENT ME! */
    ArrayList<Movie> tempMovies = new ArrayList<Movie>();
    for(Movie movie : movies)
    {
      tempMovies.add(movie);
    }

    for (int i = 1; i < tempMovies.size(); i++)
    {
      Movie temp = tempMovies.get(i);
      int possibleIndex = i;
      while (possibleIndex > 0 && temp.getUserRating() > tempMovies.get(possibleIndex - 1).getUserRating())
      {
        tempMovies.set(i, tempMovies.get(possibleIndex - 1));
        possibleIndex--;
        i--;
      }
      tempMovies.set(possibleIndex, temp);
    }

    ArrayList<Movie> top50 = new ArrayList<Movie>();
    for (Movie movie : tempMovies)
    {
      if (top50.size() < 50)
      {
        top50.add(movie);
      }
    }

    for (int i = 0; i < top50.size(); i++)
    {
      for (int j = i + 1; j < movies.size(); j++)
      {
        if (movies.get(j).getUserRating() > top50.get(i).getUserRating())
        {
          movies.add(i, movies.get(j));
          movies.remove(50);
        }
      }
    }

    for (int i = 0; i < top50.size(); i++)
    {
      String title = top50.get(i).getTitle();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + ": " + top50.get(i).getUserRating());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRevenue()
  {
    /* TASK 6: IMPLEMENT ME! */
    ArrayList<Movie> tempMovies = new ArrayList<Movie>();
    for(Movie movie : movies)
    {
      tempMovies.add(movie);
    }
    for (int i = 1; i < tempMovies.size(); i++)
    {
      Movie temp = tempMovies.get(i);
      int possibleIndex = i;
      while (possibleIndex > 0 && temp.getRevenue() > tempMovies.get(possibleIndex - 1).getRevenue())
      {
        tempMovies.set(i, tempMovies.get(possibleIndex - 1));
        possibleIndex--;
      }
      tempMovies.set(possibleIndex, temp);
    }

    ArrayList<Movie> top50 = new ArrayList<Movie>();
    for (Movie movie : tempMovies)
    {
      if (top50.size() < 50)
      {
        top50.add(movie);
      }
    }

    for (int i = 0; i < top50.size(); i++)
    {
      for (int j = i + 1; j < movies.size(); j++)
      {
        if (movies.get(j).getRevenue() > top50.get(i).getRevenue())
        {
          movies.add(i, movies.get(j));
          movies.remove(50);
        }
      }
    }

    for (int i = 0; i < top50.size(); i++)
    {
      String title = top50.get(i).getTitle();

      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + ": " + top50.get(i).getRevenue());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void importMovieList(String fileName)
  {
    /* TASK 1: IMPLEMENT ME! */
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] movieFromCSV = line.split(",");

        // pull out the data for this cereal
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);


        // create Cereal object to store values
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres,userRating, year, revenue);

        // adding Cereal object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}
