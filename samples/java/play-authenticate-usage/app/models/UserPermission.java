begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|models
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
name|models
operator|.
name|Permission
import|;
end_import

begin_import
import|import
name|io
operator|.
name|ebean
operator|.
name|Finder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Entity
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Id
import|;
end_import

begin_comment
comment|/**  * Initial version based on work by Steve Chaloner (steve@objectify.be) for  * Deadbolt2  */
end_comment

begin_class
annotation|@
name|Entity
specifier|public
class|class
name|UserPermission
extends|extends
name|AppModel
implements|implements
name|Permission
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Id
specifier|public
name|Long
name|id
decl_stmt|;
specifier|public
name|String
name|value
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Finder
argument_list|<
name|Long
argument_list|,
name|UserPermission
argument_list|>
name|find
init|=
operator|new
name|Finder
argument_list|<>
argument_list|(
name|UserPermission
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
specifier|public
specifier|static
name|UserPermission
name|findByValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
name|find
operator|.
name|query
argument_list|()
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"value"
argument_list|,
name|value
argument_list|)
operator|.
name|findUnique
argument_list|()
return|;
block|}
block|}
end_class

end_unit

