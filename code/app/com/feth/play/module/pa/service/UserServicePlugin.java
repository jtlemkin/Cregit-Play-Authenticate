begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|service
package|;
end_package

begin_import
import|import
name|play
operator|.
name|Application
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Plugin
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|PlayAuthenticate
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|UserServicePlugin
extends|extends
name|Plugin
implements|implements
name|UserService
block|{
specifier|private
name|Application
name|application
decl_stmt|;
specifier|public
name|UserServicePlugin
parameter_list|(
specifier|final
name|Application
name|app
parameter_list|)
block|{
name|application
operator|=
name|app
expr_stmt|;
block|}
specifier|protected
name|Application
name|getApplication
parameter_list|()
block|{
return|return
name|application
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onStart
parameter_list|()
block|{
if|if
condition|(
name|PlayAuthenticate
operator|.
name|hasUserService
argument_list|()
condition|)
block|{
name|Logger
operator|.
name|warn
argument_list|(
literal|"A user service was already registered - replacing the old one, however this might hint to a configuration problem"
argument_list|)
expr_stmt|;
block|}
name|PlayAuthenticate
operator|.
name|setUserService
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

