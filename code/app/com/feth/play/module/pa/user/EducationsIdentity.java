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
name|user
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_interface
specifier|public
interface|interface
name|EducationsIdentity
block|{
class|class
name|EducationInfo
implements|implements
name|Serializable
block|{
specifier|protected
name|String
name|id
decl_stmt|;
specifier|protected
name|String
name|schoolName
decl_stmt|;
specifier|protected
name|String
name|degree
decl_stmt|;
specifier|protected
name|int
name|startDateYear
decl_stmt|;
specifier|protected
name|int
name|endDateYear
decl_stmt|;
specifier|public
name|EducationInfo
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|schoolName
parameter_list|,
name|String
name|degree
parameter_list|,
name|int
name|startDateYear
parameter_list|,
name|int
name|endDateYear
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|schoolName
operator|=
name|schoolName
expr_stmt|;
name|this
operator|.
name|degree
operator|=
name|degree
expr_stmt|;
name|this
operator|.
name|startDateYear
operator|=
name|startDateYear
expr_stmt|;
name|this
operator|.
name|endDateYear
operator|=
name|endDateYear
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
specifier|public
name|String
name|getSchoolName
parameter_list|()
block|{
return|return
name|schoolName
return|;
block|}
specifier|public
name|String
name|getDegree
parameter_list|()
block|{
return|return
name|degree
return|;
block|}
specifier|public
name|int
name|getStartDateYear
parameter_list|()
block|{
return|return
name|startDateYear
return|;
block|}
specifier|public
name|int
name|getEndDateYear
parameter_list|()
block|{
return|return
name|endDateYear
return|;
block|}
block|}
specifier|public
name|Collection
argument_list|<
name|EducationInfo
argument_list|>
name|getEducations
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

