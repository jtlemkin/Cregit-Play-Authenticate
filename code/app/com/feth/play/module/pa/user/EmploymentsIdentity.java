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
name|util
operator|.
name|Collection
import|;
end_import

begin_interface
specifier|public
interface|interface
name|EmploymentsIdentity
block|{
specifier|public
specifier|static
class|class
name|EmploymentInfo
block|{
specifier|protected
name|String
name|id
decl_stmt|;
specifier|protected
name|String
name|title
decl_stmt|;
specifier|protected
name|String
name|summary
decl_stmt|;
specifier|protected
name|int
name|startDateMonth
decl_stmt|;
specifier|protected
name|int
name|startDateYear
decl_stmt|;
specifier|protected
name|int
name|endDateMonth
decl_stmt|;
specifier|protected
name|int
name|endDateYear
decl_stmt|;
specifier|protected
name|boolean
name|isCurrent
decl_stmt|;
specifier|protected
name|String
name|companyName
decl_stmt|;
specifier|public
name|EmploymentInfo
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|summary
parameter_list|,
name|int
name|startDateMonth
parameter_list|,
name|int
name|startDateYear
parameter_list|,
name|int
name|endDateMonth
parameter_list|,
name|int
name|endDateYear
parameter_list|,
name|boolean
name|isCurrent
parameter_list|,
name|String
name|companyName
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
name|title
operator|=
name|title
expr_stmt|;
name|this
operator|.
name|summary
operator|=
name|summary
expr_stmt|;
name|this
operator|.
name|startDateMonth
operator|=
name|startDateMonth
expr_stmt|;
name|this
operator|.
name|startDateYear
operator|=
name|startDateYear
expr_stmt|;
name|this
operator|.
name|endDateMonth
operator|=
name|endDateMonth
expr_stmt|;
name|this
operator|.
name|endDateYear
operator|=
name|endDateYear
expr_stmt|;
name|this
operator|.
name|isCurrent
operator|=
name|isCurrent
expr_stmt|;
name|this
operator|.
name|companyName
operator|=
name|companyName
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
name|getTitle
parameter_list|()
block|{
return|return
name|title
return|;
block|}
specifier|public
name|String
name|getSummary
parameter_list|()
block|{
return|return
name|summary
return|;
block|}
specifier|public
name|int
name|getStartDateMonth
parameter_list|()
block|{
return|return
name|startDateMonth
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
name|getEndDateMonth
parameter_list|()
block|{
return|return
name|endDateMonth
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
specifier|public
name|boolean
name|isCurrent
parameter_list|()
block|{
return|return
name|isCurrent
return|;
block|}
specifier|public
name|String
name|getCompanyName
parameter_list|()
block|{
return|return
name|companyName
return|;
block|}
block|}
specifier|public
name|Collection
argument_list|<
name|EmploymentInfo
argument_list|>
name|getEmployments
parameter_list|()
function_decl|;
block|}
end_interface

end_unit
