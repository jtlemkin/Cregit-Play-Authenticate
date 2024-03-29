begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|security
package|;
end_package

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|DeadboltHandler
import|;
end_import

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|ExecutionContextProvider
import|;
end_import

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|cache
operator|.
name|HandlerCache
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

begin_import
import|import
name|javax
operator|.
name|inject
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|inject
operator|.
name|Singleton
import|;
end_import

begin_class
annotation|@
name|Singleton
specifier|public
class|class
name|MyHandlerCache
implements|implements
name|HandlerCache
block|{
specifier|private
specifier|final
name|DeadboltHandler
name|defaultHandler
decl_stmt|;
specifier|private
specifier|final
name|PlayAuthenticate
name|auth
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|MyHandlerCache
parameter_list|(
specifier|final
name|PlayAuthenticate
name|auth
parameter_list|,
specifier|final
name|ExecutionContextProvider
name|execContextProvider
parameter_list|)
block|{
name|this
operator|.
name|auth
operator|=
name|auth
expr_stmt|;
name|this
operator|.
name|defaultHandler
operator|=
operator|new
name|MyDeadboltHandler
argument_list|(
name|auth
argument_list|,
name|execContextProvider
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|DeadboltHandler
name|apply
parameter_list|(
specifier|final
name|String
name|key
parameter_list|)
block|{
return|return
name|this
operator|.
name|defaultHandler
return|;
block|}
annotation|@
name|Override
specifier|public
name|DeadboltHandler
name|get
parameter_list|()
block|{
return|return
name|this
operator|.
name|defaultHandler
return|;
block|}
block|}
end_class

end_unit

