package com.example.android.datafrominternet.main;

/**
 * Created by Rahul B Gautam on 4/17/18.
 */
public interface MainView {

    /**
     * Makes ResultsTextView visible
     */
    public void showResultsTextView();

    public void hideResultsTextView();

    public void showErrorMessageTextView();

    public void hideErrorMessageTextView();

    public void setErrorMessageTextView(int text);

    /**
     * Makes the progressbar VISIBLE
     */
    public void showProgress();

    /**
     * Makes the progressbar INVISIBLE
     */
    public void hideProgress();

    /**
     * Sets the text in the ResultsTextView
     */
    public void setResultsTextView(String text);

    /**
     * Sets the error message
     */
    public void showErrorMessage();

    /**
     * @return text from edittext
     */
    public String getSearchStringEditText();

    public void setUrlDisplayTextView(String text);
}
