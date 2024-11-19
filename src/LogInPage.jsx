import iOLogo from './assets/iO-logo.jpg';
import styles from './LogInPage.module.css';

const LogInPage = () => {
    return (
      <div className={styles.container}>
        <img src={iOLogo} alt="iO Logo" className={styles.logo} />
        <form className={styles.form}>
          
          <div className={styles.inputGroup}>
            <label htmlFor="nameOrEmail">Name/Email</label>
            <input
              type="text"
              id="nameOrEmail"
              name="nameOrEmail"
              placeholder="Enter your name or email"
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Enter your password"
              required
            />
          </div>
          <button type="submit" className={styles.submitButton}>
            Log In
          </button>
        </form>
      </div>
    );
  };


export default LogInPage; 

