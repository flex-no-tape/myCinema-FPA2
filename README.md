// S4091095, Fletcher Brown
// COSC2391 Further Programming 2026 S2 - Assignment 2: MyCinema
//
// Y Signup
// Y Login
// Y Dashboard displays the user first name and last name
// Y User profile can be edited (first name and/or last name)
// Y Bookings can be created
// Y Booking validation is implemented
// Y Bookings can be edited (screening cinema/date/time, from the booking history screen)
// Y Bookings can be cancelled
// Y Data is stored and restored using JDBC with SQLite
// Y User can view all bookings (own bookings only, newest first)
// Y User can export records to a text file (user chooses file name and location)
// Y User password update implemented (Edit Account screen)
// N Password hashing/encryption implemented (passwords are currently stored as plain text)
// N JUnit test cases included
// Y Design pattern implemented in addition to MVC: DAO (Data Access Object)
// Y Database setup/run instructions: see below
//
// ---------------------------------------------------------------------------
// Architecture
// ---------------------------------------------------------------------------
// MVC:
//   Model       - models package (User, Movie, Screening, Booking)
//   View        - views package (FXML files: Login, SignUp, Dashboard, Booking,
//                 History, Account)
//   Controller  - controllers package (one controller per view, handles events,
//                 input validation and screen switching)
// DAO:
//   dao package (UserDAO, MovieDAO, ScreeningDAO, BookingDAO). All JDBC/SQL is
//   kept inside the DAO classes, so controllers never touch SQL directly.
// Other:
//   utils.DatabaseManager - opens SQLite connections and creates the tables
//   utils.PopulateDB      - inserts demo movies and screenings
//   utils.UserSession     - holds the currently logged in user
//   utils.ExportBookings  - writes the booking history to a text file
//
// ---------------------------------------------------------------------------
// Database setup / run instructions
// ---------------------------------------------------------------------------
// 1. Open the project in preferred IDE.
// 2. Please ensure JavaFX and the SQLite JDBC driver are on the project
//    classpath.
// 3. Run the main class: application.MyCinemaApp
// 4. On first launch the SQLite file MyCinema.db is created automatically in the
//    project's working directory, along with the users, movies, screenings and
//    bookings tables. No manual database setup is needed.
// 5. The movies and screenings tables are automatically populated with
//    demonstration data on launch (10 movies, 5 screenings each).
// 6. If MyCinema.db is deleted, the app starts cleanly with no users and new
//    users can sign up.
//
// ---------------------------------------------------------------------------
// Notes
// ---------------------------------------------------------------------------
// - Passwords must be at least 8 characters with a number, an uppercase letter
//   and a special character from: ! @ # $ % ^ & * ( ) _ - = + ?
// - Booking notes are limited to 50 words; at least one ticket (adult or child)
//   and seat information are required.
// - Bookings are limited to the screenings stored in the database; each movie
//   has a fixed set of cinema/date/time sessions to choose from.
// - Search/filtering of movies and screenings is not implemented.
