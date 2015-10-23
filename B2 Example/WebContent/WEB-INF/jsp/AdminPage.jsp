<%@ taglib uri="/bbNG" prefix="bbNG"%>
<bbNG:genericPage>
<bbNG:pageHeader>
<bbNG:pageTitleBar title="BlackBoard Migration"></bbNG:pageTitleBar>
</bbNG:pageHeader>
<bbNG:form id="AdminForm" method = "POST" action = "${actionUrl}" enctype="application/x-www-form-urlencoded">
			<bbNG:dataCollection>
				<bbNG:step id="Step" title="Code" instructions= "Type your Code">

					<bbNG:dataElement label = "Code">
						
					</bbNG:dataElement>
					
					${msg}
				</bbNG:step>
				<bbNG:stepSubmit showCancelButton= "False"><bbNG:stepSubmitButton id="SubmitButton" label="Submit"></bbNG:stepSubmitButton></bbNG:stepSubmit>
			</bbNG:dataCollection>
		</bbNG:form>	
	
</bbNG:genericPage>
