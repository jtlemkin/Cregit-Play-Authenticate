begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonNode
import|;
end_import

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
name|data
operator|.
name|DynamicForm
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|validation
operator|.
name|ValidationError
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|validation
operator|.
name|Constraints
operator|.
name|RequiredValidator
import|;
end_import

begin_import
import|import
name|play
operator|.
name|i18n
operator|.
name|Lang
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|F
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

begin_comment
comment|/** * * Simple (JUnit) tests that can call all parts of a play app. * If you are interested in mocking a whole application, see the wiki for more details. * */
end_comment

begin_class
specifier|public
class|class
name|ApplicationTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|simpleCheck
parameter_list|()
block|{
name|int
name|a
init|=
literal|1
operator|+
literal|1
decl_stmt|;
name|assertThat
argument_list|(
name|a
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
comment|//    @Test
comment|//    public void renderTemplate() {
comment|//        Content html = views.html.index.render("Your new application is ready.");
comment|//        assertThat(contentType(html)).isEqualTo("text/html");
comment|//        assertThat(contentAsString(html)).contains("Your new application is ready.");
comment|//    }
block|}
end_class

end_unit

