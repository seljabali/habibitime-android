//
//  PhraseDetailsViewController.h
//  Habibi
//
//  Created by B2BConnect on 19/07/14.
//
//

#import <UIKit/UIKit.h>
#import <AVFoundation/AVAudioPlayer.h>

@interface PhraseDetailsViewController : UIViewController
{
    
    IBOutlet UILabel *lblPhrase1;
    IBOutlet UILabel *lblPhraseTranslation1;
    IBOutlet UILabel *lblPhraseTranslation2;
    IBOutlet UILabel *lblPhraseTranslation3;
    IBOutlet UILabel *toGenderLabel;

}
@property(nonatomic,strong)NSString *catName;
@property(nonatomic,strong) NSNumber *habibiPhrase_id;
@property(nonatomic,strong) NSString *strHabibiPhrase;
@property (strong, nonatomic) IBOutlet UIButton *btnFemaleSound;
@property (strong, nonatomic) IBOutlet UIButton *btnMaleSound;

@property (nonatomic, strong) AVAudioPlayer *player;
- (IBAction)copyBtnOnAction:(id)sender;
- (IBAction)playSoundOnClick:(id)sender;
- (IBAction)PlaySoundToMale:(id)sender;
- (IBAction)playSoundToFemale:(id)sender;

@end
