//
//  SettingsViewController.h
//  Habibi
//
//  Created by B2BConnect on 20/07/14.
//
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@interface SettingsViewController : UIViewController
{
    
    IBOutlet UIButton *btnMyGenderM;
    IBOutlet UIButton *btnMyGenderF;
    IBOutlet UIButton *btnMale;
    IBOutlet UIButton *btnFemale;
}
- (IBAction)maleRadioBtnAction:(id)sender;
- (IBAction)femaleRadioBtnAction:(id)sender;
- (IBAction)myGenderMaleOnClick:(id)sender;
- (IBAction)myGenderFemaleOnClick:(id)sender;
@end
