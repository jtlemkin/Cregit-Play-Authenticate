begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|*
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|*
import|;
end_import

begin_import
import|import
name|play
operator|.
name|test
operator|.
name|*
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|F
operator|.
name|*
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|test
operator|.
name|Helpers
operator|.
name|*
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|fest
operator|.
name|assertions
operator|.
name|Assertions
operator|.
name|*
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|fluentlenium
operator|.
name|core
operator|.
name|filter
operator|.
name|FilterConstructor
operator|.
name|*
import|;
end_import

begin_class
specifier|public
class|class
name|IntegrationTest
block|{
comment|/**      * add your integration test here      * in this example we just check if the welcome page is being shown      */
annotation|@
name|Test
specifier|public
name|void
name|test
parameter_list|()
block|{
name|running
argument_list|(
name|testServer
argument_list|(
literal|3333
argument_list|,
name|fakeApplication
argument_list|(
name|inMemoryDatabase
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|HTMLUNIT
argument_list|,
operator|new
name|Callback
argument_list|<
name|TestBrowser
argument_list|>
argument_list|()
block|{
specifier|public
name|void
name|invoke
parameter_list|(
name|TestBrowser
name|browser
parameter_list|)
block|{
name|browser
operator|.
name|goTo
argument_list|(
literal|"http://localhost:3333"
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|browser
operator|.
name|pageSource
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
literal|"Your new application is ready."
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

