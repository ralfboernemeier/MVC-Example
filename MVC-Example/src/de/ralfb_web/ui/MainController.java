package de.ralfb_web.ui;

import de.ralfb_web.model.Model;
import de.ralfb_web.services.DAOService;
import de.ralfb_web.utils.DAOServiceInjectable;
import de.ralfb_web.utils.ExceptionListener;
import de.ralfb_web.utils.ModelInjectable;

import javafx.beans.value.ObservableValue;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MainController implements ExceptionListener, ModelInjectable, DAOServiceInjectable {

	/**
	 * Constructor
	 */
	public MainController() {
		super();
		serviceWorkerTask1.messageProperty().addListener((obs, oldMsg, newMsg) -> messages.appendText(newMsg + "\n"));
	}

	/**
	 * Fields
	 */
	private Model model;
	private DAOService dao;

	@Override
	public void setDAO(DAOService dao) {
		this.dao = dao;

	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public void exceptionOccurred(Throwable th) {
		String msg = String.valueOf(th);
		messages.appendText(msg + "\n");
	}

	/**
	 * Initialize Method will be executed after all FXML Nodes are ready. This is
	 * the place for Initialization of Nodes.
	 */
	public void initialize() {
		/**
		 * Register ExceptionListener with DAOService class to get Exceptions from
		 * DAOService class in Controller for showing in the View.
		 */
		dao.registerExceptionListener(th -> {
			model.setExceptionOccured(true);
			String msg = String.valueOf(th);
			messages.appendText(msg + "\n");
		});

		// Add Listener to the TextFields to recognize changes and set the data in the
		// model
		host.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setHost(newValue);
			model.setDbConnectString();
		});

		port.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setPort(newValue);
			model.setDbConnectString();
		});

		sid.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setSid(newValue);
			model.setDbConnectString();
		});

		user.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setUser(newValue);
			model.setDbConnectString();
		});

		password.textProperty().addListener((observable, oldValue, newValue) -> {
			model.setPassword(newValue);
			model.setDbConnectString();
		});

		// Create a ToggleGroup for the RadioButtons
		ToggleGroup group = new ToggleGroup();
		oracle.setToggleGroup(group);
		oracle.setSelected(true);
		model.setDbVendor("Oracle");
		mysql.setToggleGroup(group);
		sqlite.setToggleGroup(group);

		// Add a ChangeListener to get the value of the selected button in the group.
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				// Has selection.
				if (group.getSelectedToggle() != null) {
					RadioButton button = (RadioButton) group.getSelectedToggle();
					model.setDbVendor(button.getText());
				}
			}
		});

		// Add a Listener to the BooleanProperty getExceptionOccured.
		model.getExceptionOccured().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				setFadeTransitionLabelErrorStatus(true);
			}
		});

	}

	/**
	 * Initialize FXML Nodes (fx:id)
	 */
	@FXML
	Button exit;

	@FXML
	Button connectDB;

	@FXML
	TextArea messages;

	@FXML
	TextField host;

	@FXML
	TextField port;

	@FXML
	TextField sid;

	@FXML
	TextField user;

	@FXML
	TextField password;

	@FXML
	ProgressIndicator pi = new ProgressIndicator();

	@FXML
	ImageView imageNotOk = new ImageView();

	@FXML
	RadioButton oracle;

	@FXML
	RadioButton mysql;

	@FXML
	RadioButton sqlite;

	/**
	 * Initialize and set FadeTransition Objects. The FadTransition will be used to
	 * overlay the ProgressIndicator with an image that indicates an error in case
	 * of an error during the service task.
	 */
	FadeTransition fade1 = new FadeTransition(Duration.millis(300), imageNotOk);
	FadeTransition fade2 = new FadeTransition(Duration.millis(300), pi);

	/**
	 * Method that set the opacity of the imageNotOK, which indicates an error, to 1
	 * if boolean error = true. If boolean error = false set opacity of imjageNotOK
	 * to 0 and opacity of ProgressIndicator to 1.
	 * 
	 * @param error Boolean: Show error image = true / Show ProgressIndicator =
	 *              false
	 */
	public void setFadeTransitionLabelErrorStatus(boolean error) {
		if (error) {
			fade1.setFromValue(0.0);
			fade1.setToValue(1.0);
			fade1.setAutoReverse(false);
			fade1.setCycleCount(1);
			fade1.setOnFinished(e -> imageNotOk.setOpacity(1.0));
			fade2.setFromValue(1.0);
			fade2.setToValue(0.0);
			fade2.setAutoReverse(false);
			fade2.setCycleCount(1);
			fade2.setOnFinished(e -> pi.setOpacity(0.0));
			ParallelTransition pt = new ParallelTransition(fade1, fade2);
			pt.play();
		} else {
			fade1.setFromValue(1.0);
			fade1.setToValue(0.0);
			fade1.setAutoReverse(false);
			fade1.setCycleCount(1);
			fade1.setOnFinished(e -> imageNotOk.setOpacity(0.0));
			fade2.setFromValue(0.0);
			fade2.setToValue(1.0);
			fade2.setAutoReverse(false);
			fade2.setCycleCount(1);
			fade2.setOnFinished(e -> pi.setOpacity(1.0));
			ParallelTransition pt = new ParallelTransition(fade1, fade2);
			pt.play();
		}
	}

	/**
	 * Method that will be executed if the exit Button was clicked. This Method will
	 * fire a Window Event WINDOW_CLOSE_REQUEST. This event will be handled by
	 * "Stage.setOnCloseRequest(event { ... }); defined when creating the Stage.
	 * 
	 * @param event ActionEvent
	 */
	public void exitButtonTapped(ActionEvent event) {
		// Cast the Window of UI Control Button exit to Stage
		((Stage) exit.getScene().getWindow())
				.fireEvent(new WindowEvent(((Stage) exit.getScene().getWindow()), WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Method that will be executed if the "Connect Database" Button is tapped.
	 */
	@SuppressWarnings("unchecked")
	public void connectButtonTapped() {
		messages.setText("");
		// reset excpetionOcuured BooleanProperty in model class
		model.setExceptionOccured(false);
		pi.setVisible(true);
		pi.progressProperty().bind(serviceWorkerTask1.progressProperty());
		setFadeTransitionLabelErrorStatus(false);
		if (!serviceWorkerTask1.isRunning()) {
			serviceWorkerTask1.reset();
			serviceWorkerTask1.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

				@Override
				public void handle(WorkerStateEvent t) {
					messages.setText((String) t.getSource().getValue() + "\n");
				}
			});
			serviceWorkerTask1.start();
		}
	}

	/**
	 * Service serviceWorkerTask1 will be started as a new service. Task will get
	 * JDBC and Database version info.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	Service serviceWorkerTask1 = new Service() {

		@Override
		protected Task createTask() {
			return new Task() {
				@Override
				protected String call() throws Exception {
					// Start entering execution code here ...
					model.setDbVersionProperty(dao.getDbVersionInfo(model.getDbVendor().getValue(), model.getUser(),
							model.getPassword(), model.getHost(), Integer.parseInt(model.getPort()), model.getSid()));
					if (model.getDbVersionProperty().getValue() == null) {
						serviceWorkerTask1.onFailedProperty();
					}
					return model.getDbVersionProperty().getValue();
				}

				@Override
				protected void succeeded() {
					super.succeeded();
					updateProgress(1.0, 1.0);
					updateMessage("Done!");
				}

				@Override
				protected void cancelled() {
					super.cancelled();
					updateMessage("Cancelled!");
				}

				@Override
				protected void failed() {
					super.failed();
					updateMessage("Failed!");
				}
			};
		}
	};

}
